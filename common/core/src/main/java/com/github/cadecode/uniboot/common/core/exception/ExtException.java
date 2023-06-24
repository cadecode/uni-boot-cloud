package com.github.cadecode.uniboot.common.core.exception;

/**
 * 扩展异常
 *
 * @author Cade Li
 * @since 2023/6/24
 */
public class ExtException extends RuntimeException {
    public ExtException() {
        super();
    }

    public ExtException(String message) {
        super(message);
    }

    public ExtException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExtException(Throwable cause) {
        super(cause);
    }

    protected ExtException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
