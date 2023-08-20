package com.github.cadecode.uniboot.common.plugin.mq.exception;

/**
 * 事务消息异常
 *
 * @author Cade Li
 * @since 2023/8/20
 */
public class TxMsgException extends RuntimeException {
    public TxMsgException() {
        super();
    }

    public TxMsgException(String message) {
        super(message);
    }

    public TxMsgException(String message, Throwable cause) {
        super(message, cause);
    }

    public TxMsgException(Throwable cause) {
        super(cause);
    }

    protected TxMsgException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
