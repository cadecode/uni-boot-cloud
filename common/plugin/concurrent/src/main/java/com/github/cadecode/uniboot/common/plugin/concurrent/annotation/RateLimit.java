package com.github.cadecode.uniboot.common.plugin.concurrent.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 限流注解
 *
 * @author Cade Li
 * @date 2022/9/4
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {


    @AliasFor("limitPerSecond")
    double value() default 1;

    /**
     * 每秒限制数量
     */
    @AliasFor("value")
    double limitPerSecond() default 1;

    /**
     * 等待超时时间
     * <0 表示时间不限
     * =0 表示不等待，直接返回结果
     * >0 表示超时时间
     */
    long waitTimeout() default -1;

    /**
     * 等待时间单位
     */
    TimeUnit waitTimeUnit() default TimeUnit.SECONDS;

    /**
     * 预热时间，单位毫秒
     * < 0 表示不需要预热
     */
    long warmupMillis() default -1;
}
