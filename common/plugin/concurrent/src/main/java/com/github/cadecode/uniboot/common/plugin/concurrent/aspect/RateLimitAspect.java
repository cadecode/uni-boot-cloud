package com.github.cadecode.uniboot.common.plugin.concurrent.aspect;

import com.github.cadecode.uniboot.common.plugin.concurrent.annotation.RateLimit;
import com.github.cadecode.uniboot.common.plugin.concurrent.exception.RateLimitException;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 限流器切面
 *
 * @author Cade Li
 * @date 2022/9/4
 */
@Slf4j
@Aspect
@Component
public class RateLimitAspect {

    private static final ConcurrentHashMap<String, RateLimiter> LIMITER_MAP = new ConcurrentHashMap<>();

    @Pointcut("@within(com.github.cadecode.uniboot.common.plugin.concurrent.annotation.RateLimit) " +
            "|| @annotation(com.github.cadecode.uniboot.common.plugin.concurrent.annotation.RateLimit)")
    public void pointCut() {

    }

    @Before("pointCut()")
    public void rateLimit(JoinPoint point) {
        // 获取方法上的注解
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        // 获取注解
        RateLimit rateLimit = methodSignature.getMethod().getAnnotation(RateLimit.class);
        // 获取 RateLimiter
        RateLimiter rateLimiter = LIMITER_MAP.computeIfAbsent(methodSignature.toLongString(), m -> {
            // 判断是否需要预热
            if (rateLimit.warmupMillis() < 0) {
                return RateLimiter.create(rateLimit.limitPerSecond());
            }
            return RateLimiter.create(rateLimit.limitPerSecond(), rateLimit.warmupMillis(), TimeUnit.MILLISECONDS);
        });
        // 判断是否需要阻塞等待
        if (rateLimit.waitTimeout() < 0) {
            rateLimiter.acquire();
            return;
        }
        boolean acquireOk;
        if (rateLimit.waitTimeout() > 0) {
            acquireOk = rateLimiter.tryAcquire(rateLimit.waitTimeout(), rateLimit.waitTimeUnit());
        } else {
            acquireOk = rateLimiter.tryAcquire();
        }
        if (!acquireOk) {
            throw new RateLimitException();
        }
    }
}
