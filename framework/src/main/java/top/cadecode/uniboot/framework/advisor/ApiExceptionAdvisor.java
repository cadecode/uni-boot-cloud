package top.cadecode.uniboot.framework.advisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.cadecode.uniboot.common.core.exception.UniErrorCode;
import top.cadecode.uniboot.common.core.exception.UniException;
import top.cadecode.uniboot.common.core.web.response.ApiResult;

/**
 * UniException 统一处理器
 *
 * @author Cade Li
 * @date 2022/5/8
 */
@Slf4j
@RestControllerAdvice
public class ApiExceptionAdvisor {

    /**
     * 处理 UniException
     */
    @ExceptionHandler(UniException.class)
    public ApiResult<Object> handleApiException(UniException e) {
        log.error("Api Exception =>", e);
        return ApiResult.error(e.getErrorCode()).moreInfo(e.getMoreInfo());
    }

    /**
     * 兜底处理一般异常
     */
    @ExceptionHandler(Exception.class)
    public ApiResult<Object> handleException(Exception e) {
        log.error("Exception =>", e);
        return ApiResult.error(UniErrorCode.UNKNOWN).moreInfo(e.getMessage());
    }
}
