package top.cadecode.uniboot.common.plugin.cache.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Redis 配置类
 *
 * @author Cade Li
 * @date 2022/5/24
 */
@RequiredArgsConstructor
@Configuration
public class RedisConfig {

    private final RedisConnectionFactory factory;

    @Bean
    public RedisTemplate<String, ?> jsonRedisTemplate() {
        RedisTemplate<String, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        // 设置 k v 的序列化方式
        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(RedisSerializer.json());
        // 设置 hash k v 的序列化方式
        template.setHashKeySerializer(RedisSerializer.string());
        template.setHashValueSerializer(RedisSerializer.json());
        return template;
    }
}
