package top.cadecode.uniboot.common.core.annotation;

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
     * 是否阻塞等待
     */
    boolean blockWait() default false;

    /**
     * 等待时间
     */
    long time() default 0;

    /**
     * 等待时间单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
