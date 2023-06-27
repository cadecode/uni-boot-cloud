package com.github.cadecode.uniboot.common.plugin.cache.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.github.cadecode.uniboot.common.plugin.cache.listener.RedisMessageListener;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.List;

/**
 * Redis 配置类
 *
 * @author Cade Li
 * @date 2022/5/24
 */
@RequiredArgsConstructor
@Configuration
public class RedisConfig {

    /**
     * 监听器自动注册
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory factory, List<RedisMessageListener> listeners) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        // 注册监听器
        listeners.forEach(o -> container.addMessageListener(o, o.topics()));
        return container;
    }

    /**
     * 全局默认 RedisTemplate
     * key string, val json
     */
    @Primary
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory, ObjectMapper objectMapper) {
        // 创建 ObjectMapper，并复用 Spring 项目配置
        ObjectMapper newObjectMapper = objectMapper.copy();
        // 启用序列化类型
        newObjectMapper.activateDefaultTyping(newObjectMapper.getPolymorphicTypeValidator(), DefaultTyping.NON_FINAL, As.PROPERTY);
        // 创建 Redis Jackson 序列化器
        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer(newObjectMapper);
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        // 设置 k v 的序列化方式
        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(jsonRedisSerializer);
        // 设置 hash k v 的序列化方式
        template.setHashKeySerializer(RedisSerializer.string());
        template.setHashValueSerializer(jsonRedisSerializer);
        return template;
    }
}
