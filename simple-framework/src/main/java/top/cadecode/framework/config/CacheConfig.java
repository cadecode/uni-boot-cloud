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
import top.cadecode.common.util.TokenUtil;

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

    private final TokenUtil tokenUtil;
    private final RedisConnectionFactory factory;

    @Bean("securityCacheManager")
    public CacheManager securityCacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        // 更新时间设置为 jwt token 配置时间
        caffeineCacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(tokenUtil.getExpiration(), TimeUnit.SECONDS));
        caffeineCacheManager.setAllowNullValues(true);
        return caffeineCacheManager;
    }

    @Bean("redisTemplate")
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        // 设置 k v 的序列化方式
        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(RedisSerializer.json());
        template.setHashKeySerializer(RedisSerializer.string());
        template.setHashValueSerializer(RedisSerializer.json());
        return template;
    }
}
