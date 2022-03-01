package top.cadecode.sa.common.core.exception;

import lombok.Getter;
import top.cadecode.sa.common.core.response.ResultCode;

/**
 * @author Cade Li
 * @date 2021/7/16
 * @description 自定义异常类总类
 */
@Getter
public class BaseException extends RuntimeException {

    private final ResultCode resultCode;
    private String errorMsg;

    public BaseException(ResultCode resultCode) {
        super(resultCode.getReason());
        this.resultCode = resultCode;
    }

    public BaseException(ResultCode resultCode, String errorMsg) {
        this(resultCode);
        this.errorMsg = errorMsg;
    }

    /**
     * 通过响应码构造通用异常对象
     *
     * @param resultCode 响应码
     * @return BaseException
     */
    public static BaseException of(ResultCode resultCode) {
        return new BaseException(resultCode);
    }

    /**
     * 设置错误信息
     *
     * @param errorMsg 错误信息
     * @return BaseException
     */
    public BaseException errorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    /**
     * 添加原始异常
     *
     * @param throwable 原始异常
     * @return BaseException
     */
    public BaseException suppressed(Throwable throwable) {
        addSuppressed(throwable);
        return this;
    }

    @Override
    public String getMessage() {
        if (this.errorMsg == null) {
            return this.getResultCode().getReason();
        }
        return "[" + this.getResultCode().getReason() + "] " + this.errorMsg;
    }
}
