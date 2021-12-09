package top.cadecode.common.core.exception;

import lombok.Getter;
import top.cadecode.common.core.response.ResponseCode;

/**
 * @author Cade Li
 * @date 2021/7/16
 * @description 自定义异常类总类
 */
@Getter
public class CommonException extends RuntimeException {

    private final ResponseCode responseCode;
    public String errorMsg;

    public CommonException(ResponseCode responseCode) {
        super(responseCode.getReason());
        this.responseCode = responseCode;
    }

    public CommonException(ResponseCode responseCode, String errorMsg) {
        this(responseCode);
        this.errorMsg = errorMsg;
    }

    /**
     * 通过响应码构造通用异常对象
     *
     * @param responseCode 响应码
     * @return CommonException
     */
    public static CommonException of(ResponseCode responseCode) {
        return new CommonException(responseCode);
    }

    /**
     * 设置错误信息
     *
     * @param errorMsg 错误信息
     * @return CommonException
     */
    public CommonException errorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    /**
     * 添加原始异常
     *
     * @param throwable 原始异常
     * @return CommonException
     */
    public CommonException suppressed(Throwable throwable) {
        addSuppressed(throwable);
        return this;
    }

    @Override
    public String getMessage() {
        if (this.errorMsg == null) {
            return this.getResponseCode().getReason();
        }
        return "[" + this.getResponseCode().getReason() + "] " + this.errorMsg;
    }
}
