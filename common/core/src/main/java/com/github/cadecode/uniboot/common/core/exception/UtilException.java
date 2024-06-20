package com.github.cadecode.uniboot.common.core.exception;

/**
 * 工具类异常
 *
 * @author Cade Li
 * @date 2023/6/9
 */
public class UtilException extends BaseException {
    public UtilException() {
    }

    public UtilException(String message, Object... params) {
        super(message, params);
    }

    public UtilException(String message, Throwable cause, Object... params) {
        super(message, cause, params);
    }

    public UtilException(Throwable cause) {
        super(cause);
    }

    public UtilException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... params) {
        super(message, cause, enableSuppression, writableStackTrace, params);
    }
}
