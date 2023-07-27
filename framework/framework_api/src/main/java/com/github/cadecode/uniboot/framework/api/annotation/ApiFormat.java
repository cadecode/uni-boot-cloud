package com.github.cadecode.uniboot.framework.api.annotation;

import java.lang.annotation.*;

/**
 * 是否开启 ApiResult 格式化
 *
 * @author Cade Li
 * @date 2022/5/8
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiFormat {
    boolean value() default true;
}
