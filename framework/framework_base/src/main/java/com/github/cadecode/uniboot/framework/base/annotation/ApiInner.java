package com.github.cadecode.uniboot.framework.base.annotation;

import java.lang.annotation.*;

/**
 * 标识供内部调用的接口
 * 当有些内部调用不方便传递 token 时（比如异步线程中的 feign 调用，无法直接拿到 token 请求头等）
 * 可以将接口配置到 security ignore url 列表中，将不再需要登录
 *
 * @author Cade Li
 * @since 2023/8/1
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiInner {

    /**
     * 是否仅供内部调用
     * 用于防止接口被来自网关的流量随意访问
     */
    boolean onlyClient() default false;

    /**
     * 是否需要用户登录
     * 用于防止接口被未登录访问
     */
    boolean requireUser() default false;
}
