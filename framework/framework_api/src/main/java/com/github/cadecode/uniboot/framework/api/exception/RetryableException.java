package com.github.cadecode.uniboot.framework.api.exception;

import lombok.Getter;

/**
 * 可重试异常
 *
 * @author Cade Li
 * @date 2023/7/4
 */
@Getter
public class RetryableException extends RuntimeException {

    /**
     * 用于传递重试中的状态信息
     */
    private Object state;

    public RetryableException() {
    }

    public RetryableException(String message) {
        super(message);
    }

    public RetryableException(String message, Throwable cause) {
        super(message, cause);
    }

    public RetryableException(Throwable cause) {
        super(cause);
    }

    public RetryableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public RetryableException(Object state) {
        this.state = state;
    }

    public RetryableException(String message, Object state) {
        super(message);
        this.state = state;
    }

    public RetryableException(String message, Throwable cause, Object state) {
        super(message, cause);
        this.state = state;
    }

    public RetryableException(Throwable cause, Object state) {
        super(cause);
        this.state = state;
    }

    public RetryableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object state) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.state = state;
    }
}
