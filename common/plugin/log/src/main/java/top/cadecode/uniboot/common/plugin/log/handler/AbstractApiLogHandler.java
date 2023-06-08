package top.cadecode.uniboot.common.plugin.log.handler;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import top.cadecode.uniboot.common.plugin.log.annotation.ApiLogger;
import top.cadecode.uniboot.common.plugin.log.aspect.ApiLoggerAspect.BaseLogInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Api Log 处理器抽象
 *
 * @author Cade Li
 * @since 2023/6/8
 */
@Slf4j
public abstract class AbstractApiLogHandler {

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
}
