package top.cadecode.uniboot.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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

    public static StringRedisTemplate TEMPLATE;

    private StringRedisTemplate stringRedisTemplate;

    /**
     * 自动注入 stringRedisTemplate
     */
    @Autowired(required = false)
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 获取 value
     */
    public static <T> T get(String key, Class<T> clazz) {
        String json = TEMPLATE.opsForValue().get(key);
        return JacksonUtil.toBean(json, clazz);
    }

    /**
     * 获取 value
     */
    public static <T> T get(String key, TypeReference<T> typeReference) {
        String json = TEMPLATE.opsForValue().get(key);
        return JacksonUtil.toBean(json, typeReference);
    }

    /**
     * 添加 key
     */
    public static void set(String key, Object o) {
        String json = JacksonUtil.toJson(o);
        TEMPLATE.opsForValue().set(key, json);
    }

    /**
     * 添加 key，如果不存在
     */
    public static Boolean setIfAbsent(String key, Object o) {
        String json = JacksonUtil.toJson(o);
        return TEMPLATE.opsForValue().setIfAbsent(key, json);
    }

    /**
     * 添加 key，如果不存在
     */
    public static Boolean setIfPresent(String key, Object o) {
        String json = JacksonUtil.toJson(o);
        return TEMPLATE.opsForValue().setIfPresent(key, json);
    }

    /**
     * 添加 key 并设置有效期
     */
    public static void set(String key, Object o, long timeout, TimeUnit timeUnit) {
        String json = JacksonUtil.toJson(o);
        TEMPLATE.opsForValue().set(key, json, timeout, timeUnit);
    }

    /**
     * 添加 key 并设置有效期，如果不存在
     */
    public static Boolean setIfAbsent(String key, Object o, long timeout, TimeUnit timeUnit) {
        String json = JacksonUtil.toJson(o);
        return TEMPLATE.opsForValue().setIfAbsent(key, json, timeout, timeUnit);
    }

    /**
     * 添加 key 并设置有效期，如果不存在
     */
    public static Boolean setIfPresent(String key, Object o, long timeout, TimeUnit timeUnit) {
        String json = JacksonUtil.toJson(o);
        return TEMPLATE.opsForValue().setIfPresent(key, json, timeout, timeUnit);
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
        TEMPLATE = stringRedisTemplate;
        if (Objects.isNull(TEMPLATE)) {
            throw new IllegalArgumentException("Bean stringRedisTemplate not found");
        }
    }
}
