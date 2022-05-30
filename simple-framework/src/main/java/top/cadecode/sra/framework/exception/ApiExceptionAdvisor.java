package top.cadecode.sra.framework.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.cadecode.sra.common.exception.ApiErrorCode;
import top.cadecode.sra.common.exception.ApiException;
import top.cadecode.sra.common.response.ApiResult;

/**
 * @author Cade Li
 * @date 2022/5/8
 * @description ApiException 统一处理器
 */
@Slf4j
@RestControllerAdvice
public class ApiExceptionAdvisor {

    /**
     * 处理 ApiException
     */
    @ExceptionHandler(ApiException.class)
    public ApiResult<Object> handleApiException(ApiException e) {
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
