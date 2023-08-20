package com.github.cadecode.uniboot.common.plugin.log.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.github.cadecode.uniboot.common.plugin.log.annotation.ApiLogger;
import com.github.cadecode.uniboot.common.plugin.log.model.BaseLogInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Api Log 处理器抽象
 *
 * @author Cade Li
 * @since 2023/6/8
 */
@Slf4j
public abstract class AbstractApiLogHandler {

    private static final Set<String> IGNORE_LOG_FIELD_SET = CollUtil.newHashSet("password");

    public abstract Object generateLog(ProceedingJoinPoint point, BaseLogInfo baseLogInfo);

    public abstract void save(ApiLogger apiLogger, Object o);

    /**
     * 获取方法参数名和参数值
     */
    public static Map<String, Object> getRequestParams(JoinPoint joinPoint, ApiLogger apiLogger) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String[] names = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        if (ObjUtil.isEmpty(names) || ObjUtil.isEmpty(args)) {
            return Collections.emptyMap();
        }
        if (names.length != args.length) {
            log.error("API log [{}]: method [{}] param and the pass value do not match", apiLogger.type(), methodSignature.getName());
            return Collections.emptyMap();
        }
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < names.length; i++) {
            // 对敏感字段进行排除
            if (IGNORE_LOG_FIELD_SET.contains(names[i])) {
                map.put(names[i], "[IGNORE_LOG_FIELD]");
                continue;
            }
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
}
