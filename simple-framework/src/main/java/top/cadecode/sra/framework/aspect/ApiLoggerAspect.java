package top.cadecode.sra.framework.aspect;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONUtil;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.cadecode.sra.common.annotation.ApiLogger;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Cade Li
 * @date 2021/12/4
 * @description 请求响应信息日志 AOP 类
 */
@Slf4j
@Aspect
@Component
public class ApiLoggerAspect {

    /**
     * 环绕通知
     *
     * @param point 切入点
     * @return 原方法返回值
     * @throws Throwable 异常信息
     */
    @Around("@within(apiLogger) || @annotation(apiLogger)")
    public Object around(ProceedingJoinPoint point, ApiLogger apiLogger) throws Throwable {
        // 根据注解判断是否开启
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        ApiLogger mResponseLogging = methodSignature.getMethod().getAnnotation(ApiLogger.class);
        boolean loggingFlag;
        if (mResponseLogging != null) {
            loggingFlag = mResponseLogging.value();
        } else {
            loggingFlag = methodSignature.getMethod()
                    .getDeclaringClass()
                    .getAnnotation(ApiLogger.class)
                    .value();
        }
        if (!loggingFlag) {
            return point.proceed();
        }
        // 开启处理日志
        long startTime = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        Object result = point.proceed();
        // 解析 user-agent，生成日志信息
        String userAgentStr = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgentUtil.parse(userAgentStr);
        ResponseLoggingInfo loggingInfo = ResponseLoggingInfo.builder()
                .threadId(Long.toString(Thread.currentThread().getId()))
                .threadName(Thread.currentThread().getName())
                .classMethod(point.getSignature().getDeclaringTypeName() + '.' + point.getSignature().getName())
                .ip(ServletUtil.getClientIP(request))
                .url(request.getRequestURL().toString())
                .httpMethod(request.getMethod())
                .requestParams(ApiLoggerAspect.getRequestParams(point))
                .result(result)
                .timeCost(System.currentTimeMillis() - startTime)
                .userAgent(userAgentStr)
                .browser(userAgent.getBrowser().getName())
                .os(userAgent.getOs().getName())
                .build();
        log.info("API LOG => {}", JSONUtil.toJsonStr(loggingInfo));
        return result;
    }

    /**
     * 获取方法参数名和参数值
     */
    public static Map<String, Object> getRequestParams(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String[] names = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        if (ObjectUtils.isEmpty(names) || ObjectUtils.isEmpty(args)) {
            return Collections.emptyMap();
        }
        if (names.length != args.length) {
            log.warn("API LOG => Method [{}] has different numbers of parameter names and values", methodSignature.getName());
            return Collections.emptyMap();
        }
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < names.length; i++) {
            map.put(names[i], args[i]);
        }
        return map;
    }

    /**
     * 请求日志信息类
     */
    @Data
    @Builder
    static class ResponseLoggingInfo {
        // 线程id
        private String threadId;
        // 线程名称
        private String threadName;
        // 类方法
        private String classMethod;
        // ip
        private String ip;
        // url
        private String url;
        // http 方法
        private String httpMethod;
        // 请求参数
        private Object requestParams;
        // 返回参数
        private Object result;
        // 接口耗时
        private Long timeCost;
        // 操作系统
        private String os;
        // 浏览器
        private String browser;
        // user-agent
        private String userAgent;
    }
}
