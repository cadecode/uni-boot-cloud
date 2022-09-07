package top.cadecode.sra.framework.aspect;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import top.cadecode.sra.common.annotation.RateLimit;
import top.cadecode.sra.common.enums.error.FrameErrorEnum;
import top.cadecode.sra.common.exception.ApiException;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Cade Li
 * @date 2022/9/4
 * @description 限流器切面
 */
@Slf4j
@Aspect
@Component
public class RateLimitAspect {

    private static final ConcurrentHashMap<String, RateLimiter> LIMITER_MAP = new ConcurrentHashMap<>();

    @Pointcut("@within(top.cadecode.sra.common.annotation.RateLimit) " +
            "|| @annotation(top.cadecode.sra.common.annotation.RateLimit)")
    public void pointCut() {

    }

    @Before("pointCut()")
    public void rateLimit(JoinPoint point) {
        // 获取方法上的注解
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        // 获取注解
        RateLimit rateLimit = methodSignature.getMethod().getAnnotation(RateLimit.class);
        // 获取 RateLimiter
        RateLimiter rateLimiter = LIMITER_MAP.computeIfAbsent(methodSignature.toLongString(), m -> RateLimiter.create(rateLimit.limitPerSecond()));
        // 判断是否需要阻塞等待
        if (!rateLimit.blockWait()) {
            boolean acquireOk = false;
            if (rateLimit.time() != 0) {
                acquireOk = rateLimiter.tryAcquire(rateLimit.time(), rateLimit.timeUnit());
            } else {
                acquireOk = rateLimiter.tryAcquire();
            }
            if (acquireOk) {
                return;
            }
            throw ApiException.of(FrameErrorEnum.REQUEST_RATE_LIMITED, "请稍后再尝试访问");
        }
        rateLimiter.acquire();
    }
}
