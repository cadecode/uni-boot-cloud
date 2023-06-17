package top.cadecode.uniboot.common.plugin.cache.l2cache.cache;

import cn.hutool.core.util.ObjectUtil;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.data.redis.core.RedisTemplate;
import top.cadecode.uniboot.common.plugin.cache.exception.DLCacheException;
import top.cadecode.uniboot.common.plugin.cache.l2cache.DLCacheProperties;
import top.cadecode.uniboot.common.plugin.cache.l2cache.sync.DLCacheRefreshMsg;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Double Level Cache
 * 基于 spring boot cache AbstractValueAdaptingCache
 * 本地缓存基于 caffeine，远程缓存基于 redis
 * 实现
 *
 * @author Cade Li
 * @date 2023/6/15
 */
@Slf4j
@Getter
public class DLCache extends AbstractValueAdaptingCache {

    private final String name;
    private final long expiration;
    private final DLCacheProperties cacheProperties;
    private final Cache<String, Object> caffeineCache;
    private final RedisTemplate<String, Object> redisTemplate;

    public DLCache(String name, long expiration, DLCacheProperties cacheProperties,
                   Cache<String, Object> caffeineCache, RedisTemplate<String, Object> redisTemplate) {
        super(cacheProperties.isAllowNullValues());
        this.name = name;
        this.expiration = expiration;
        this.cacheProperties = cacheProperties;
        this.caffeineCache = caffeineCache;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getNativeCache() {
        return this;
    }

    @Override
    protected Object lookup(Object key) {
        String redisKey = getRedisKey(key);
        Object val;
        val = caffeineCache.getIfPresent(key);
        if (ObjectUtil.isNotNull(val)) {
            log.debug("DLCache local get cache, key:{}, value:{}", key, val);
            return val;
        }
        val = redisTemplate.opsForValue().get(redisKey);
        if (ObjectUtil.isNotNull(val)) {
            log.debug("DLCache remote get cache, key:{}, value:{}", key, val);
            caffeineCache.put(key.toString(), val);
            return val;
        }
        return val;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        T val;
        val = (T) lookup(key);
        if (ObjectUtil.isNotNull(val)) {
            return val;
        }
        // 双检锁
        synchronized (key.toString().intern()) {
            val = (T) lookup(key);
            if (ObjectUtil.isNotNull(val)) {
                return val;
            }
            try {
                // 拦截的业务方法
                val = valueLoader.call();
                // 加入缓存
                put(key, val);
            } catch (Exception e) {
                throw new DLCacheException("DLCache valueLoader fail", e);
            }
            return val;
        }
    }

    @Override
    public void put(Object key, Object value) {
        // val 不能为空，但传了空，就清理该 key
        if (!cacheProperties.isAllowNullValues() && ObjectUtil.isNull(value)) {
            evict(key);
            return;
        }
        putRemote(key, value);
        sync(key);
        putLocal(key, value);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        String redisKey = getRedisKey(key);
        // 获取旧值
        Object oldVal = redisTemplate.opsForValue().get(redisKey);
        if (ObjectUtil.isNotNull(oldVal)) {
            return toValueWrapper(oldVal);
        }
        // 若允许值为空，且 key 存在，但值为空
        if (cacheProperties.isAllowNullValues() && ObjectUtil.equal(redisTemplate.hasKey(redisKey), true)) {
            return toValueWrapper(oldVal);
        }
        // val 不能为空，但传了空，就清理该 key
        if (!cacheProperties.isAllowNullValues() && ObjectUtil.isNull(value)) {
            evict(key);
            return toValueWrapper(oldVal);
        }
        Boolean setOkFlag;
        if (expiration > 0) {
            setOkFlag = redisTemplate.opsForValue().setIfAbsent(redisKey, value, expiration, TimeUnit.MILLISECONDS);
        } else {
            setOkFlag = redisTemplate.opsForValue().setIfAbsent(redisKey, value);
        }
        if (ObjectUtil.equal(setOkFlag, true)) {
            sync(key);
            putLocal(key, value);
        }
        return toValueWrapper(oldVal);

    }

    @Override
    public void evict(Object key) {
        // 先清理 redis 再清理 caffeine
        redisTemplate.delete(getRedisKey(key));
        sync(key);
        clearLocal(key);
    }

    @Override
    public void clear() {
        // 先清理 redis 再清理 caffeine
        Set<String> keys = redisTemplate.keys(getRedisKey("*"));
        if (ObjectUtil.isNotEmpty(keys)) {
            keys.forEach(redisTemplate::delete);
        }
        sync(null);
        clearLocal(null);
    }

    private void sync(Object key) {
        String syncTopic = cacheProperties.getRemote().getSyncTopic();
        redisTemplate.convertAndSend(syncTopic, new DLCacheRefreshMsg(name, key));
    }

    private void putLocal(Object key, Object value) {
        caffeineCache.put(key.toString(), value);
    }

    private void putRemote(Object key, Object value) {
        if (expiration > 0) {
            redisTemplate.opsForValue().set(getRedisKey(key), value, expiration, TimeUnit.MILLISECONDS);
            return;
        }
        redisTemplate.opsForValue().set(getRedisKey(key), value);
    }

    /**
     * 获取 redis 完整 key
     */
    public String getRedisKey(Object key) {
        // 双冒号，与 spring cache 默认一致
        return this.name.concat("::").concat(key.toString());
    }

    public void clearLocal(Object key) {
        if (ObjectUtil.isNull(key)) {
            caffeineCache.invalidateAll();
        } else {
            caffeineCache.invalidate(key);
        }
    }
}
