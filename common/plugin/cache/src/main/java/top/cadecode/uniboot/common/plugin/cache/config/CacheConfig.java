package top.cadecode.uniboot.common.plugin.cache.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import top.cadecode.uniboot.common.plugin.cache.consts.CacheConst;
import top.cadecode.uniboot.common.plugin.cache.l2cache.DLCacheProperties;
import top.cadecode.uniboot.common.plugin.cache.l2cache.cache.DLCacheManager;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * SpringBoot Cache 配置类
 *
 * @author Cade Li
 * @date 2022/1/5
 */
@RequiredArgsConstructor
@Configuration
@EnableCaching
@EnableConfigurationProperties(DLCacheProperties.class)
public class CacheConfig {

    /**
     * Caffeine 本地缓存
     * 过期时间 5 s
     *
     * @return CaffeineCacheManager 实例
     */
    @Bean(name = CacheConst.CCM_5S)
    public CaffeineCacheManager caffeineCacheManager5s() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        // 过期时间设置为 5 s
        caffeineCacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(5, TimeUnit.SECONDS));
        caffeineCacheManager.setAllowNullValues(true);
        return caffeineCacheManager;
    }

    /**
     * Redis 缓存
     * 过期时间 5 分钟
     *
     * @return RedisCacheManager 实例
     */
    @Bean(name = CacheConst.RCM_5M)
    public RedisCacheManager redisCacheManager5m(RedisTemplate<String, Object> redisTemplate) {
        return geneRedisCacheManager(redisTemplate, 5);
    }

    /**
     * Redis 缓存
     * 过期时间 30 分钟
     *
     * @return RedisCacheManager 实例
     */
    @Primary
    @Bean(name = CacheConst.RCM_30M)
    public RedisCacheManager redisCacheManager30m(RedisTemplate<String, Object> redisTemplate) {
        return geneRedisCacheManager(redisTemplate, 30);
    }

    private static RedisCacheManager geneRedisCacheManager(RedisTemplate<String, Object> redisTemplate, long minutes) {
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(SerializationPair.fromSerializer(redisTemplate.getStringSerializer()))
                .serializeValuesWith(SerializationPair.fromSerializer(redisTemplate.getValueSerializer()))
                .entryTtl(Duration.ofMinutes(minutes));
        return RedisCacheManager.builder(redisTemplate.getConnectionFactory())
                .cacheDefaults(cacheConfiguration)
                .transactionAware()
                .build();
    }

    @Bean(name = CacheConst.DL)
    public DLCacheManager dlCacheManager(DLCacheProperties cacheProperties, RedisTemplate<String, Object> redisTemplate) {
        return new DLCacheManager(cacheProperties, redisTemplate);
    }
}
