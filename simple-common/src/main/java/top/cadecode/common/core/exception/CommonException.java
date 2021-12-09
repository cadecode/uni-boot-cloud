package top.cadecode.common.core.exception;

import lombok.Getter;
import top.cadecode.common.enums.ErrorCode;

/**
 * @author Cade Li
 * @date 2021/7/16
 * @description 自定义异常类总类
 */
@Getter
public class CommonException extends RuntimeException {

    private final ErrorCode errorCode;

    public CommonException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public CommonException(ErrorCode errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
    }
}
