package top.cadecode.uniboot.common.plugin.cache.exception;

/**
 * Redis 相关异常
 *
 * @author Cade Li
 * @date 2023/6/9
 */
public class RedisLockException extends RuntimeException {
    public RedisLockException() {
    }

    public RedisLockException(String message) {
        super(message);
    }

    public RedisLockException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedisLockException(Throwable cause) {
        super(cause);
    }

    public RedisLockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
