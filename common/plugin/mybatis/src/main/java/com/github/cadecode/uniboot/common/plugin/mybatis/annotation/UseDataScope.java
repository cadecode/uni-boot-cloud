package com.github.cadecode.uniboot.common.plugin.mybatis.annotation;

import java.lang.annotation.*;

/**
 * 数据权限注解
 *
 * @author Cade Li
 * @since 2024/5/23
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UseDataScope {

    String DEFAULT_FIELD = "data_scope";
    String DEFAULT_RESOLVER_NAME = "defaultDataScopeResolver";

    /**
     * 是否启用过滤
     */
    boolean enableFilter() default true;

    /**
     * 需要作用的字段名
     */
    String field() default "";

    /**
     * 权限范围解析器 Bean 名称
     */
    String resolverName() default "";
}
