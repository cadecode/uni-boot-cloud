package top.cadecode.uniboot.common.plugin.cache.util;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Redis 工具类
 *
 * @author Cade Li
 * @date 2022/5/29
 */
@Component
public class RedisUtil implements InitializingBean {

    private static RedisTemplate<String, Object> TEMPLATE;

    private RedisTemplate<String, Object> redisTemplate;

    public static RedisTemplate<String, Object> getTemplate() {
        return TEMPLATE;
    }

    /**
     * 自动注入 stringRedisTemplate
     */
    @Autowired(required = false)
    public void setStringRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 获取 value，String 类型
     */
    public static String get(String key) {
        return get(key, String.class);
    }

    /**
     * 获取 value，指定类型
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key, Class<T> clazz) {
        return (T) TEMPLATE.opsForValue().get(key);
    }

    /**
     * 获取 value，指定类型
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key, TypeReference<T> typeReference) {
        return (T) TEMPLATE.opsForValue().get(key);
    }

    /**
     * 添加 key
     */
    public static void set(String key, Object o) {
        TEMPLATE.opsForValue().set(key, o);
    }

    /**
     * 添加 key，如果不存在
     */
    public static Boolean setIfAbsent(String key, Object o) {
        return TEMPLATE.opsForValue().setIfAbsent(key, o);
    }

    /**
     * 添加 key，如果不存在
     */
    public static Boolean setIfPresent(String key, Object o) {
        return TEMPLATE.opsForValue().setIfPresent(key, o);
    }

    /**
     * 添加 key 并设置有效期
     */
    public static void set(String key, Object o, long timeout, TimeUnit timeUnit) {
        TEMPLATE.opsForValue().set(key, o, timeout, timeUnit);
    }

    /**
     * 添加 key 并设置有效期，如果不存在
     */
    public static Boolean setIfAbsent(String key, Object o, long timeout, TimeUnit timeUnit) {
        return TEMPLATE.opsForValue().setIfAbsent(key, o, timeout, timeUnit);
    }

    /**
     * 添加 key 并设置有效期，如果不存在
     */
    public static Boolean setIfPresent(String key, Object o, long timeout, TimeUnit timeUnit) {
        return TEMPLATE.opsForValue().setIfPresent(key, o, timeout, timeUnit);
    }

    /**
     * 删除 key
     */
    public static Boolean del(String key) {
        return TEMPLATE.delete(key);
    }

    /**
     * 是否存在 key
     */
    public static Boolean has(String key) {
        return TEMPLATE.hasKey(key);
    }

    /**
     * 设置 key 过期时间
     */
    public static Boolean expire(String key, long timeout, TimeUnit timeUnit) {
        return TEMPLATE.expire(key, timeout, timeUnit);
    }

    @Override
    public void afterPropertiesSet() {
        TEMPLATE = redisTemplate;
        if (Objects.isNull(TEMPLATE)) {
            throw new IllegalArgumentException("Bean redisTemplate not found");
        }
    }
}
