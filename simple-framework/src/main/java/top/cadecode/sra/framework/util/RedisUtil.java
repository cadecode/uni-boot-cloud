package top.cadecode.sra.framework.util;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author Cade Li
 * @date 2022/5/29
 * @description Redis 工具类
 */
@Component
@RequiredArgsConstructor
public class RedisUtil implements InitializingBean {

    /**
     * 自动注入 redisTemplate
     */
    private final RedisTemplate<String, Object> redisTemplate;

    private static RedisTemplate<String, Object> TEMPLATE;

    /**
     * 获取 value 并强转
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key, Class<T> clazz) {
        return (T) TEMPLATE.opsForValue().get(key);
    }

    /**
     * 获取 value 并强转，带泛型
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key, TypeReference<T> typeReference) {
        return (T) TEMPLATE.opsForValue().get(key);
    }

    /**
     * 添加 key
     */
    public static void set(String key, Object o, long timeout, TimeUnit timeUnit) {
        TEMPLATE.opsForValue().set(key, o, timeout, timeUnit);
    }

    /**
     * 删除 key
     */
    public static Boolean del(String key) {
        return TEMPLATE.delete(key);
    }

    /**
     * 删除 key
     */
    public static Boolean del(String key, long timeout, TimeUnit timeUnit) {
        return TEMPLATE.expire(key, timeout, timeUnit);
    }

    @Override
    public void afterPropertiesSet() {
        TEMPLATE = redisTemplate;
    }
}
