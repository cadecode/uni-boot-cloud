package top.cadecode.uniboot.common.plugin.cache.exception;

/**
 * Redis 过期处理异常
 *
 * @author Cade Li
 * @date 2023/6/13
 */
public class RedisExHandleException extends RuntimeException {

    public RedisExHandleException() {
        super();
    }

    public RedisExHandleException(String message) {
        super(message);
    }

    public RedisExHandleException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedisExHandleException(Throwable cause) {
        super(cause);
    }

    protected RedisExHandleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
