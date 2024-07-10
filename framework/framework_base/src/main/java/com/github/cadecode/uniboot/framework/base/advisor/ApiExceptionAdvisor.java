package com.github.cadecode.uniboot.framework.base.advisor;

import com.github.cadecode.uniboot.common.core.enums.ApiErrorCode;
import com.github.cadecode.uniboot.common.core.exception.ApiException;
import com.github.cadecode.uniboot.common.core.web.response.ApiResult;
import com.github.cadecode.uniboot.framework.api.enums.FrameErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ApiException 统一处理器
 *
 * @author Cade Li
 * @date 2022/5/8
 */
@Slf4j
@RestControllerAdvice
public class ApiExceptionAdvisor {

    /**
     * 处理 ApiException
     */
    @ExceptionHandler(ApiException.class)
    public ApiResult<Object> handleApiException(ApiException e) {
        // 特殊处理接口返回 null 的情况
        if (FrameErrorEnum.RES_BODY_NULL.getCode().equals(e.getErrorCode().getCode())) {
            return ApiResult.ok(null);
        }
        log.error("Api Exception =>", e);
        return ApiResult.error(e.getErrorCode()).moreInfo(e.getMoreInfo());
    }

    /**
     * 兜底处理一般异常
     */
    @ExceptionHandler(Exception.class)
    public ApiResult<Object> handleException(Exception e) {
        log.error("Exception =>", e);
        return ApiResult.error(ApiErrorCode.UNKNOWN).moreInfo(e.getMessage());
    }
}
