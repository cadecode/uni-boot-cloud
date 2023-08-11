package com.github.cadecode.uniboot.framework.api.plugin;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.github.cadecode.uniboot.common.core.util.JacksonUtil;
import com.github.cadecode.uniboot.common.plugin.log.annotation.ApiLogger;
import com.github.cadecode.uniboot.common.plugin.log.aspect.ApiLoggerAspect.BaseLogInfo;
import com.github.cadecode.uniboot.common.plugin.log.handler.AbstractApiLogHandler;
import com.github.cadecode.uniboot.framework.api.bean.dto.SysLogDto.SysLogSaveDto;
import com.github.cadecode.uniboot.framework.api.feignclient.SysLogClient;
import com.github.cadecode.uniboot.framework.api.util.SecurityUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Api Log 处理器实现
 *
 * @author Cade Li
 * @since 2023/6/8
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class ApiLogHandler extends AbstractApiLogHandler {

    private final SysLogClient sysLogClient;

    /**
     * 构造 LogInfo
     */
    public SysLogSaveDto generateLog(ProceedingJoinPoint point, BaseLogInfo baseLogInfo) {
        ApiLogger apiLogger = baseLogInfo.getApiLogger();
        // 解析 user-agent，生成日志信息
        String userAgentStr = baseLogInfo.getRequest().getHeader("User-Agent");
        UserAgent userAgent = UserAgentUtil.parse(userAgentStr);
        // 解析参数
        String paramsJson;
        try {
            paramsJson = JacksonUtil.toJson(getRequestParams(point, apiLogger));
        } catch (Exception e) {
            paramsJson = ExceptionUtil.stacktraceToString(e);
            log.error("API log [{}]: request params to json fail", apiLogger.type().getType(), e);
        }
        // 获取描述
        String description = apiLogger.description();
        if (ObjectUtil.isEmpty(description)) {
            ApiOperation apiOperation = ((MethodSignature) point.getSignature()).getMethod().getAnnotation(ApiOperation.class);
            if (ObjectUtil.isNotNull(apiOperation)) {
                description = apiOperation.value();
            }
        }
        return SysLogSaveDto.builder()
                .logType(apiLogger.type())
                .classMethod(point.getSignature().getDeclaringTypeName() + '.' + point.getSignature().getName())
                .exceptional(baseLogInfo.getExceptional())
                .accessUser(SecurityUtil.getUsername())
                .description(description)
                .url(baseLogInfo.getRequest().getRequestURL().toString())
                .threadId(Long.toString(Thread.currentThread().getId()))
                .threadName(Thread.currentThread().getName())
                .ip(ServletUtil.getClientIP(baseLogInfo.getRequest()))
                .httpMethod(baseLogInfo.getRequest().getMethod())
                .requestParams(paramsJson)
                .result(baseLogInfo.getResultStr())
                .timeCost(baseLogInfo.getTimeCost())
                .userAgent(userAgentStr)
                .browser(userAgent.getBrowser().getName())
                .os(userAgent.getOs().getName())
                .build();
    }

    /**
     * 持久化日志
     */
    @Async
    public void save(ApiLogger apiLogger, Object o) {
        if (!apiLogger.enableSave()) {
            return;
        }
        SysLogSaveDto dto = (SysLogSaveDto) o;
        if (!apiLogger.saveParams()) {
            dto.setRequestParams(null);
        }
        if (!apiLogger.saveResult()) {
            dto.setResult(null);
        }
        try {
            sysLogClient.save(Collections.singletonList(dto));
        } catch (Exception e) {
            log.error("API log [{}]: save fail", apiLogger.type().getType(), e);
        }
    }
}
