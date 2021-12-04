package top.cadecode.common.response;

import eu.bitwalker.useragentutils.UserAgent;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.cadecode.common.util.CollectUtil;
import top.cadecode.common.util.JsonUtil;
import top.cadecode.common.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * @author Cade Li
 * @date 2021/12/4
 * @description 请求响应信息日志 AOP 类
 */
@Slf4j
@Aspect
@Component
public class ResponseLoggerAspect {

    // 本地 IP 地址
    private static final List<String> LOCAL_IP_LIST = Arrays.asList("127.0.0.1", "0:0:0:0:0:0:0:1");

    /**
     * 获取 IP 地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        // 解析请求头 X-Forwarded-For 获取主机地址
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtil.isEmpty(ip)) {
            ip = request.getRemoteAddr();
            // 获取本机真正的ip地址
            if (LOCAL_IP_LIST.contains(ip)) {
                try {
                    ip = InetAddress.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return ip;
    }

    /**
     * 获取方法参数名和参数值
     */
    public static Map<String, Object> getRequestParams(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String[] names = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        if (CollectUtil.isEmpty(names) || CollectUtil.isEmpty(args)) {
            return Collections.emptyMap();
        }
        if (names.length != args.length) {
            log.warn("方法 [{}] 参数名和参数值数量不一致", methodSignature.getName());
            return Collections.emptyMap();
        }
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < names.length; i++) {
            map.put(names[i], args[i]);
        }
        return map;
    }

    /**
     * 环绕通知
     *
     * @param point 切入点
     * @return 原方法返回值
     * @throws Throwable 异常信息
     */
    @Around("@within(responseLogger) || @annotation(responseLogger)")
    public Object around(ProceedingJoinPoint point, ResponseLogger responseLogger) throws Throwable {
        // 根据注解判断是否开启
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        ResponseLogger mResponseLogging = methodSignature.getMethod().getAnnotation(ResponseLogger.class);
        boolean loggingFlag;
        if (mResponseLogging != null) {
            loggingFlag = mResponseLogging.value();
        } else {
            loggingFlag = methodSignature.getMethod()
                    .getDeclaringClass()
                    .getAnnotation(ResponseLogger.class)
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
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);
        ResponseLoggingInfo loggingInfo = ResponseLoggingInfo.builder()
                .threadId(Long.toString(Thread.currentThread().getId()))
                .threadName(Thread.currentThread().getName())
                .classMethod(point.getSignature().toString())
                .ip(ResponseLoggerAspect.getIpAddress(request))
                .url(request.getRequestURL().toString())
                .httpMethod(request.getMethod())
                .requestParams(ResponseLoggerAspect.getRequestParams(point))
                .result(result)
                .timeCost(System.currentTimeMillis() - startTime)
                .userAgent(userAgentStr)
                .browser(userAgent.getBrowser().toString())
                .os(userAgent.getOperatingSystem().toString())
                .build();
        log.info("请求响应日志 => {}", JsonUtil.objToStr(loggingInfo));
        return result;
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
