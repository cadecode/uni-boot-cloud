package com.github.cadecode.uniboot.framework.base.advisor;

import com.github.cadecode.uniboot.common.core.enums.ApiErrorCode;
import com.github.cadecode.uniboot.common.core.web.response.ApiResult;
import com.github.cadecode.uniboot.common.plugin.concurrent.exception.RateLimitException;
import com.github.cadecode.uniboot.framework.api.enums.FrameErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * 框架异常处理器
 *
 * @author Cade Li
 * @date 2022/5/30
 */
@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class FrameExceptionAdvisor {

    /**
     * 限流异常
     */
    @ExceptionHandler(value = RateLimitException.class)
    public ApiResult<Object> handleValidationException(RateLimitException e, HttpServletRequest request) {
        log.error("RateLimit Exception =>", e);
        return ApiResult.error(FrameErrorEnum.REQUEST_RATE_LIMITED).moreInfo("请稍后再尝试访问").path(request.getRequestURI());
    }

    /**
     * 处理参数校验异常
     */
    @ExceptionHandler(value = BindException.class)
    public ApiResult<Object> handleValidationException(BindException e, HttpServletRequest request) {
        log.error("Validation Exception =>", e);
        // 获取错误信息，并拼接
        String msg = e.getBindingResult().getFieldErrors().stream()
                .map(o -> "[" + o.getField() + "]" + o.getDefaultMessage())
                .collect(Collectors.joining(","));
        return ApiResult.error(FrameErrorEnum.VALIDATED_ERROR).moreInfo(msg).path(request.getRequestURI());
    }

    /**
     * Spring MVC 异常类型和错误枚举的映射
     */
    private static final Map<Class<?>, ApiErrorCode> MVC_EXP_CODE_MAP = new HashMap<Class<?>, ApiErrorCode>() {{
        // 请求参数错误
        put(ServletRequestBindingException.class, FrameErrorEnum.REQ_PARAM_INVALID);
        // 请求体错误
        put(HttpMessageNotReadableException.class, FrameErrorEnum.REQ_BODY_INVALID);
        // content-type 错误
        put(HttpMediaTypeNotSupportedException.class, FrameErrorEnum.MEDIA_TYPE_NO_SUPPORT);
        // 参数类型转换错误
        put(TypeMismatchException.class, FrameErrorEnum.PARAM_TYPE_CONVERT_ERROR);
    }};

    /**
     * 处理 Spring MVC 参数异常
     */
    @ExceptionHandler({
            ServletRequestBindingException.class,
            HttpMessageNotReadableException.class,
            HttpMediaTypeNotSupportedException.class,
            TypeMismatchException.class
    })
    public ApiResult<Object> handleMvcException(Exception e, HttpServletRequest request) {
        log.error("Spring MVC Exception Handler =>", e);
        String requestURI = request.getRequestURI();
        // 根据异常类型查找 map 中的 code 枚举
        ApiErrorCode errorCode = MVC_EXP_CODE_MAP.entrySet()
                .stream()
                .filter(o -> o.getKey().isAssignableFrom(e.getClass()))
                .map(Entry::getValue)
                .findFirst()
                .orElse(ApiErrorCode.UNKNOWN);
        return ApiResult.error(errorCode).moreInfo(e.getMessage()).path(requestURI);
    }
}
