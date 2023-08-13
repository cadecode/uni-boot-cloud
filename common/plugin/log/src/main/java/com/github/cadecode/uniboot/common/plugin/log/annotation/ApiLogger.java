package com.github.cadecode.uniboot.common.plugin.log.annotation;


import java.lang.annotation.*;

/**
 * 用于开启打印接口日志的注解
 *
 * @author Cade Li
 * @date 2021/12/4
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiLogger {
    boolean value() default true;

    String type() default "";

    String description() default "";

    /**
     * 启用持久化
     */
    boolean enableSave() default false;

    /**
     * 保存参数
     */
    boolean saveParams() default true;

    /**
     * 保存结果
     */
    boolean saveResult() default true;
}
