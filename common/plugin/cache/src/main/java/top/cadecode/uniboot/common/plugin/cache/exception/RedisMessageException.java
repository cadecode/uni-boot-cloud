package top.cadecode.uniboot.common.plugin.cache.exception;

/**
 * Redis 过期处理异常
 *
 * @author Cade Li
 * @date 2023/6/13
 */
public class RedisMessageException extends RuntimeException {

    public RedisMessageException() {
        super();
    }

    public RedisMessageException(String message) {
        super(message);
    }

    public RedisMessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedisMessageException(Throwable cause) {
        super(cause);
    }

    protected RedisMessageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
