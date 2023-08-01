package com.github.cadecode.uniboot.common.plugin.cache.l2cache.cache;

import cn.hutool.core.util.ObjectUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.cadecode.uniboot.common.plugin.cache.exception.DLCacheException;
import com.github.cadecode.uniboot.common.plugin.cache.l2cache.DLCacheProperties;
import com.github.cadecode.uniboot.common.plugin.cache.l2cache.sync.DLCacheRefreshListener;
import com.github.cadecode.uniboot.common.plugin.cache.l2cache.sync.DLCacheRefreshMsg;
import com.github.cadecode.uniboot.common.plugin.cache.util.KeyGeneUtil;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.data.redis.core.RedisTemplate;

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
        // val 是 toStoreValue 包装过的值，为 null 则 key 不存在
        // 因为存储的 null 值被包装成了 DLCacheNullVal.INSTANCE
        if (ObjectUtil.isNotNull(val)) {
            log.trace("DLCache local get cache, key:{}, value:{}", key, val);
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
        putRemote(key, value);
        sendSyncMsg(key);
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
        Boolean setOkFlag;
        if (expiration > 0) {
            setOkFlag = redisTemplate.opsForValue().setIfAbsent(redisKey, value, expiration, TimeUnit.MILLISECONDS);
        } else {
            setOkFlag = redisTemplate.opsForValue().setIfAbsent(redisKey, value);
        }
        if (ObjectUtil.equal(setOkFlag, true)) {
            sendSyncMsg(key);
            putLocal(key, value);
        }
        return toValueWrapper(oldVal);
    }

    @Override
    public void evict(Object key) {
        // 先清理 redis 再清理 caffeine
        clearRemote(key);
        sendSyncMsg(key);
        clearLocal(key);
    }

    @Override
    public void clear() {
        // 先清理 redis 再清理 caffeine
        clearRemote(null);
        sendSyncMsg(null);
        clearLocal(null);
    }

    private void sendSyncMsg(Object key) {
        String syncTopic = cacheProperties.getRemote().getSyncTopic();
        DLCacheRefreshMsg refreshMsg = new DLCacheRefreshMsg(name, key);
        // 加入 SELF_MSG_MAP 防止自身节点重复处理
        DLCacheRefreshListener.SELF_MSG_MAP.add(refreshMsg);
        redisTemplate.convertAndSend(syncTopic, refreshMsg);
    }

    private void putLocal(Object key, Object value) {
        // toStoreValue 包装 null 值
        caffeineCache.put(key.toString(), toStoreValue(value));
    }

    private void putRemote(Object key, Object value) {
        if (expiration > 0) {
            // toStoreValue 包装 null 值
            redisTemplate.opsForValue().set(getRedisKey(key), toStoreValue(value), expiration, TimeUnit.MILLISECONDS);
            return;
        }
        redisTemplate.opsForValue().set(getRedisKey(key), toStoreValue(value));
    }

    public void clearRemote(Object key) {
        if (ObjectUtil.isNull(key)) {
            Set<String> keys = redisTemplate.keys(getRedisKey("*"));
            if (ObjectUtil.isNotEmpty(keys)) {
                keys.forEach(redisTemplate::delete);
            }
            return;
        }
        redisTemplate.delete(getRedisKey(key));
    }

    public void clearLocal(Object key) {
        if (ObjectUtil.isNull(key)) {
            caffeineCache.invalidateAll();
            return;
        }
        caffeineCache.invalidate(key);
    }

    /**
     * 检查是否允许缓存 null
     *
     * @param value 缓存值
     * @return 不为空则 true，为空但允许则 false，否则异常
     */
    private boolean checkValNotNull(Object value) {
        if (ObjectUtil.isNotNull(value)) {
            return true;
        }
        if (isAllowNullValues() && ObjectUtil.isNull(value)) {
            return false;
        }
        // val 不能为空，但传了空
        throw new DLCacheException("Check null val is not allowed");
    }

    @Override
    protected Object fromStoreValue(Object storeValue) {
        if (isAllowNullValues() && DLCacheNullVal.INSTANCE.equals(storeValue)) {
            return null;
        }
        return storeValue;
    }

    @Override
    protected Object toStoreValue(Object userValue) {
        if (!checkValNotNull(userValue)) {
            return DLCacheNullVal.INSTANCE;
        }
        return userValue;
    }

    /**
     * 获取 redis 完整 key
     */
    private String getRedisKey(Object key) {
        return KeyGeneUtil.key(this.name, key.toString());
    }

    /**
     * 在缓存时代替 null 值，以区分是 key 不存在还是 val 为 null
     */
    @Data
    public static class DLCacheNullVal {
        public static final DLCacheNullVal INSTANCE = new DLCacheNullVal();
        private String desc = "nullVal";
    }
}
