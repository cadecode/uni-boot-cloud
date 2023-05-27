package top.cadecode.uniboot.framework.aspect;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import top.cadecode.uniboot.common.annotation.ApiLogger;
import top.cadecode.uniboot.common.util.JacksonUtil;
import top.cadecode.uniboot.framework.security.TokenAuthHolder;
import top.cadecode.uniboot.system.bean.dto.SysLogDto.SysLogInfoDto;
import top.cadecode.uniboot.system.bean.po.SysLog;
import top.cadecode.uniboot.system.convert.SysLogConvert;
import top.cadecode.uniboot.system.service.SysLogService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

    private final SysLogService logService;

    @Pointcut("@within(top.cadecode.uniboot.common.annotation.ApiLogger) " +
            "|| @annotation(top.cadecode.uniboot.common.annotation.ApiLogger)")
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
            SysLogInfoDto logInfo = generateLogInfo(point, apiLogger, exceptional, attributes.getRequest(), resultStr, timeCost);
            logAndSave(apiLogger, logInfo);
        } catch (Exception e) {
            log.warn("API log [{}]: handle logger fail", apiLogger.type().getType(), e);
        }
    }

    /**
     * 构造 ResponseLogInfo
     */
    private SysLogInfoDto generateLogInfo(ProceedingJoinPoint point, ApiLogger apiLogger,
                                          Boolean exceptional, HttpServletRequest request,
                                          String resultStr, Long timeCost) {
        // 解析 user-agent，生成日志信息
        String userAgentStr = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgentUtil.parse(userAgentStr);
        // 解析参数
        String paramsJson;
        try {
            paramsJson = JacksonUtil.toJson(getRequestParams(point, apiLogger));
        } catch (Exception e) {
            paramsJson = ExceptionUtil.stacktraceToString(e);
            log.warn("API log [{}]: request params to json fail", apiLogger.type().getType(), e);
        }
        return SysLogInfoDto.builder()
                .logType(apiLogger.type())
                .classMethod(point.getSignature().getDeclaringTypeName() + '.' + point.getSignature().getName())
                .exceptional(exceptional)
                .accessUser(TokenAuthHolder.getUsername())
                .description(apiLogger.description())
                .url(request.getRequestURL().toString())
                .threadId(Long.toString(Thread.currentThread().getId()))
                .threadName(Thread.currentThread().getName())
                .ip(ServletUtil.getClientIP(request))
                .httpMethod(request.getMethod())
                .requestParams(paramsJson)
                .result(resultStr)
                .timeCost(timeCost)
                .userAgent(userAgentStr)
                .browser(userAgent.getBrowser().getName())
                .os(userAgent.getOs().getName())
                .build();
    }

    /**
     * 获取方法参数名和参数值
     */
    public static Map<String, Object> getRequestParams(JoinPoint joinPoint, ApiLogger apiLogger) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String[] names = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        if (ObjectUtils.isEmpty(names) || ObjectUtils.isEmpty(args)) {
            return Collections.emptyMap();
        }
        if (names.length != args.length) {
            log.warn("API log [{}]: method [{}] param and the pass value do not match", apiLogger.type().getType(), methodSignature.getName());
            return Collections.emptyMap();
        }
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < names.length; i++) {
            // 排除请求对象和响应
            if (args[i] instanceof HttpServletRequest || args[i] instanceof HttpServletResponse) {
                continue;
            }
            // 处理 MultipartFile
            if (args[i] instanceof MultipartFile) {
                map.put(names[i], ((MultipartFile) args[i]).getOriginalFilename());
                continue;
            }
            map.put(names[i], args[i]);
        }
        return map;
    }

    /**
     * 持久化日志
     */
    private void logAndSave(ApiLogger apiLogger, SysLogInfoDto logInfo) {
        // 打印日志
        log.info("API log [{}]: {}", apiLogger.type().getType(), JacksonUtil.toJson(logInfo));
        // 持久化
        if (!apiLogger.enableSave()) {
            return;
        }
        SysLog po = SysLogConvert.INSTANCE.dtoToPo(logInfo);
        if (!apiLogger.saveParams()) {
            po.setRequestParams(null);
        }
        if (!apiLogger.saveResult()) {
            po.setResult(null);
        }
        logService.save(po);
    }

}
