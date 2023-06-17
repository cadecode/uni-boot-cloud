package top.cadecode.uniboot.common.plugin.cache.exception;

/**
 * DLCache 异常
 *
 * @author Cade Li
 * @date 2023/6/17
 */
public class DLCacheException extends RuntimeException {
    public DLCacheException() {
        super();
    }

    public DLCacheException(String message) {
        super(message);
    }

    public DLCacheException(String message, Throwable cause) {
        super(message, cause);
    }

    public DLCacheException(Throwable cause) {
        super(cause);
    }

    protected DLCacheException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
