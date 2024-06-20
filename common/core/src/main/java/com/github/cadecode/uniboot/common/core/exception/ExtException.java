package com.github.cadecode.uniboot.common.core.exception;

/**
 * 扩展异常
 *
 * @author Cade Li
 * @since 2023/6/24
 */
public class ExtException extends BaseException {
    public ExtException() {
    }

    public ExtException(String message, Object... params) {
        super(message, params);
    }

    public ExtException(String message, Throwable cause, Object... params) {
        super(message, cause, params);
    }

    public ExtException(Throwable cause) {
        super(cause);
    }

    public ExtException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... params) {
        super(message, cause, enableSuppression, writableStackTrace, params);
    }
}
