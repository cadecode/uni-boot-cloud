package top.cadecode.uniboot.common.plugin.log.aspect;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.dtp.core.thread.DtpExecutor;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.cadecode.uniboot.common.core.util.JacksonUtil;
import top.cadecode.uniboot.common.plugin.log.annotation.ApiLogger;
import top.cadecode.uniboot.common.plugin.log.handler.AbstractApiLogHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求响应信息日志 AOP 类
 *
 * @author Cade Li
 * @date 2021/12/4
 */
@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class ApiLoggerAspect {

    private final DtpExecutor asyncExecutor;

    private final AbstractApiLogHandler apiLogHandler;

    @Pointcut("@within(top.cadecode.uniboot.common.plugin.log.annotation.ApiLogger) " +
            "|| @annotation(top.cadecode.uniboot.common.plugin.log.annotation.ApiLogger)")
    public void pointCut() {

    }

    /**
     * 环绕通知
     *
     * @param point 切入点
     * @return 原方法返回值
     * @throws Throwable 异常信息
     */
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 根据注解判断是否开启
        ApiLogger apiLogger = getApiLogger(point);
        if (!apiLogger.value()) {
            return point.proceed();
        }
        // 统计耗时
        long startTime = System.currentTimeMillis();
        Object result = null;
        Throwable throwable = null;
        try {
            result = point.proceed();
            return result;
        } catch (Throwable e) {
            throwable = e;
            throw e;
        } finally {
            handleLogger(point, apiLogger, result, throwable, System.currentTimeMillis() - startTime);
        }
    }

    /**
     * 获取 ApiLogger 注解
     */
    private ApiLogger getApiLogger(ProceedingJoinPoint point) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        ApiLogger apiLogger = methodSignature.getMethod().getAnnotation(ApiLogger.class);
        if (apiLogger == null) {
            apiLogger = methodSignature.getMethod()
                    .getDeclaringClass()
                    .getAnnotation(ApiLogger.class);
        }
        return apiLogger;
    }

    /**
     * 获取请求、结果、异常等信息，构造日志
     */
    public void handleLogger(ProceedingJoinPoint point, ApiLogger apiLogger, Object result, Throwable throwable, Long timeCost) {
        // 解析请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (ObjectUtil.isNull(attributes)) {
            return;
        }
        try {
            String resultStr = null;
            boolean exceptional = false;
            if (ObjectUtil.isNotNull(throwable)) {
                exceptional = true;
                resultStr = ExceptionUtil.stacktraceToString(throwable);
            } else {
                try {
                    resultStr = JacksonUtil.toJson(result);
                } catch (Exception e) {
                    resultStr = ExceptionUtil.stacktraceToString(e);
                    log.warn("API log [{}]: request result to json fail", apiLogger.type().getType(), e);
                }
            }
            BaseLogInfo baseLogInfo = BaseLogInfo.builder().apiLogger(apiLogger).request(attributes.getRequest())
                    .resultStr(resultStr).timeCost(timeCost).exceptional(exceptional).build();
            Object logObj = apiLogHandler.generateLog(point, baseLogInfo);
            // 打印日志
            log.info("API log [{}]: {}", apiLogger.type().getType(), JacksonUtil.toJson(logObj));
            // 持久化 异步
            asyncExecutor.execute(() -> {
                try {
                    apiLogHandler.save(apiLogger, logObj);
                } catch (Exception e) {
                    log.warn("API log [{}]: save async fail", apiLogger.type().getType(), e);
                }
            });
        } catch (Exception e) {
            log.warn("API log [{}]: handle logger fail", apiLogger.type().getType(), e);
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class BaseLogInfo {
        private ApiLogger apiLogger;
        private Boolean exceptional;
        private HttpServletRequest request;
        private String resultStr;
        private Long timeCost;
    }
}
