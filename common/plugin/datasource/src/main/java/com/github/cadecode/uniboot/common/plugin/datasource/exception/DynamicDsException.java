package com.github.cadecode.uniboot.common.plugin.datasource.exception;

/**
 * 动态数据源异常
 *
 * @author Cade Li
 * @date 2023/6/9
 */
public class DynamicDsException extends RuntimeException {
    public DynamicDsException() {
    }

    public DynamicDsException(String message) {
        super(message);
    }

    public DynamicDsException(String message, Throwable cause) {
        super(message, cause);
    }

    public DynamicDsException(Throwable cause) {
        super(cause);
    }

    public DynamicDsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
