package top.cadecode.uniboot.framework.advisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.cadecode.uniboot.common.core.enums.ApiErrorCode;
import top.cadecode.uniboot.common.core.exception.ApiException;
import top.cadecode.uniboot.common.core.web.response.ApiResult;
import top.cadecode.uniboot.framework.enums.FrameErrorEnum;

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
        log.error("Api Exception =>", e);
        // 特殊处理接口返回 null 的情况
        if (e.getErrorCode() == FrameErrorEnum.RES_BODY_NULL) {
            return ApiResult.ok(null);
        }
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
