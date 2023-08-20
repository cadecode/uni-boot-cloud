package com.github.cadecode.uniboot.framework.base.plugin.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.github.cadecode.uniboot.common.core.util.JacksonUtil;
import com.github.cadecode.uniboot.common.plugin.log.annotation.ApiLogger;
import com.github.cadecode.uniboot.common.plugin.log.handler.AbstractApiLogHandler;
import com.github.cadecode.uniboot.common.plugin.log.model.BaseLogInfo;
import com.github.cadecode.uniboot.framework.api.consts.HttpConst;
import com.github.cadecode.uniboot.framework.base.plugin.bean.po.PlgLog;
import com.github.cadecode.uniboot.framework.base.plugin.service.PlgLogService;
import com.github.cadecode.uniboot.framework.base.util.SecurityUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Log 处理器实现-save
 *
 * @author Cade Li
 * @since 2023/6/8
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class LogSaveHandler extends AbstractApiLogHandler {

    private final PlgLogService logService;

    /**
     * 构造 Log 信息对象
     */
    public PlgLog generateLog(ProceedingJoinPoint point, BaseLogInfo baseLogInfo) {
        ApiLogger apiLogger = baseLogInfo.getApiLogger();
        // 解析 user-agent
        String userAgentStr = ServletUtil.getHeader(baseLogInfo.getRequest(), HttpConst.HEAD_USER_AGENT, CharsetUtil.CHARSET_UTF_8);
        UserAgent userAgent = UserAgentUtil.parse(userAgentStr);
        // 获取 trace-id
        String traceId = ServletUtil.getHeader(baseLogInfo.getRequest(), HttpConst.HEAD_TRACE_ID, CharsetUtil.CHARSET_UTF_8);
        // 解析参数
        String paramsJson;
        try {
            paramsJson = JacksonUtil.toJson(getRequestParams(point, apiLogger));
        } catch (Exception e) {
            paramsJson = ExceptionUtil.stacktraceToString(e);
            log.error("API log [{}]: request params to json fail", apiLogger.type(), e);
        }
        // 获取描述
        String description = apiLogger.description();
        if (ObjUtil.isEmpty(description)) {
            ApiOperation apiOperation = ((MethodSignature) point.getSignature()).getMethod().getAnnotation(ApiOperation.class);
            if (ObjUtil.isNotNull(apiOperation)) {
                description = apiOperation.value();
            }
        }
        return PlgLog.builder()
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
                .traceId(traceId)
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
        PlgLog po = (PlgLog) o;
        if (!apiLogger.saveParams()) {
            po.setRequestParams(null);
        }
        if (!apiLogger.saveResult()) {
            po.setResult(null);
        }
        try {
            logService.save(po);
        } catch (Exception e) {
            log.error("API log [{}]: save fail", apiLogger.type(), e);
        }
    }
}
