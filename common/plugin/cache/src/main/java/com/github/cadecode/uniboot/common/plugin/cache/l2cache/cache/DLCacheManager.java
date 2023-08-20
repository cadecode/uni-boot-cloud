package com.github.cadecode.uniboot.common.plugin.cache.l2cache.cache;

import cn.hutool.core.util.ObjUtil;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.cadecode.uniboot.common.plugin.cache.l2cache.DLCacheProperties;
import com.github.cadecode.uniboot.common.plugin.cache.l2cache.DLCacheProperties.LocalConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 二级缓存 Manager
 * 基于 spring boot cache CacheManager
 *
 * @author Cade Li
 * @date 2023/6/15
 */
@Slf4j
@RequiredArgsConstructor
public class DLCacheManager implements CacheManager {

    private final ConcurrentHashMap<String, DLCache> cacheMap = new ConcurrentHashMap<>();

    private final DLCacheProperties cacheProperties;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public DLCache getCache(String name) {
        return cacheMap.computeIfAbsent(name, (o) -> {
            DLCache dlCache = buildCache(o);
            log.debug("Create DLCache instance, name:{}", o);
            return dlCache;
        });
    }

    private DLCache buildCache(String name) {
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder();
        // 设置过期时间 expireAfterWrite
        long expiration = 0;
        // 获取针对 cache name 设置的过期时间
        Map<String, Long> cacheExpirationMap = cacheProperties.getCacheExpirationMap();
        if (ObjUtil.isNotEmpty(cacheExpirationMap) && cacheExpirationMap.get(name) > 0) {
            expiration = cacheExpirationMap.get(name);
        } else if (cacheProperties.getDefaultExpiration() > 0) {
            expiration = cacheProperties.getDefaultExpiration();
        }
        if (expiration > 0) {
            caffeine.expireAfterWrite(expiration, TimeUnit.MILLISECONDS);
        }
        // 设置参数
        LocalConfig localConfig = cacheProperties.getLocal();
        if (ObjUtil.isNotNull(localConfig.getInitialCapacity()) && localConfig.getInitialCapacity() > 0) {
            caffeine.initialCapacity(localConfig.getInitialCapacity());

        }
        if (ObjUtil.isNotNull(localConfig.getMaximumSize()) && localConfig.getMaximumSize() > 0) {
            caffeine.maximumSize(localConfig.getMaximumSize());
        }
        return new DLCache(name, expiration, cacheProperties, caffeine.build(), redisTemplate);
    }

    @Override
    public Collection<String> getCacheNames() {
        return Collections.unmodifiableSet(cacheMap.keySet());
    }
}
