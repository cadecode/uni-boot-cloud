package top.cadecode.uniboot.common.plugin.datasource.exception;

/**
 * 动态数据源异常
 *
 * @author Cade Li
 * @date 2023/6/9
 */
public class DynamicDSException extends RuntimeException{
    public DynamicDSException() {
    }

    public DynamicDSException(String message) {
        super(message);
    }

    public DynamicDSException(String message, Throwable cause) {
        super(message, cause);
    }

    public DynamicDSException(Throwable cause) {
        super(cause);
    }

    public DynamicDSException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
