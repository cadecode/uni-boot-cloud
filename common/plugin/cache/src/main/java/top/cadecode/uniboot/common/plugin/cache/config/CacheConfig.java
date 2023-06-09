package top.cadecode.uniboot.common.plugin.cache.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

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
public class CacheConfig {

    /**
     * Caffeine 本地缓存
     * 过期时间 5 s
     *
     * @return CaffeineCacheManager 实例
     */
    @Bean
    public CacheManager caffeine5s() {
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
    @Bean
    public CacheManager redis5m(RedisTemplate<String, ?> jsonRedisTemplate) {
        return geneRedisCacheManager(jsonRedisTemplate, 5);
    }

    /**
     * Redis 缓存
     * 过期时间 30 分钟
     *
     * @return RedisCacheManager 实例
     */
    @Primary
    @Bean
    public CacheManager redis30m(RedisTemplate<String, ?> jsonRedisTemplate) {
        return geneRedisCacheManager(jsonRedisTemplate, 30);
    }

    private static RedisCacheManager geneRedisCacheManager(RedisTemplate<String, ?> jsonRedisTemplate, long minutes) {
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(SerializationPair.fromSerializer(jsonRedisTemplate.getStringSerializer()))
                .serializeValuesWith(SerializationPair.fromSerializer(jsonRedisTemplate.getValueSerializer()))
                .entryTtl(Duration.ofMinutes(minutes));
        return RedisCacheManager.builder(jsonRedisTemplate.getConnectionFactory())
                .cacheDefaults(cacheConfiguration)
                .transactionAware()
                .build();
    }
}
