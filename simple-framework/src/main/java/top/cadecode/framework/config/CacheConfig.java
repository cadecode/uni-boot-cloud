package top.cadecode.framework.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.concurrent.TimeUnit;

/**
 * @author Cade Li
 * @date 2022/1/5
 * @description SpringBoot Cache 配置类
 */
@RequiredArgsConstructor
@Configuration
@EnableCaching
public class CacheConfig {

    private final RedisConnectionFactory factory;

    /**
     * Caffeine 本地缓存
     * 过期时间 5 s
     *
     * @return CaffeineCacheManager 实例
     */
    @Bean("caffeineCache")
    public CacheManager securityCacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        // 过期时间设置为 5 s
        caffeineCacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(5, TimeUnit.SECONDS));
        caffeineCacheManager.setAllowNullValues(true);
        return caffeineCacheManager;
    }

    @Bean("redisTemplate")
    public RedisTemplate<String, ?> redisTemplate() {
        RedisTemplate<String, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        // 设置 k v 的序列化方式
        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(RedisSerializer.json());
        template.setHashKeySerializer(RedisSerializer.string());
        template.setHashValueSerializer(RedisSerializer.json());
        return template;
    }
}
