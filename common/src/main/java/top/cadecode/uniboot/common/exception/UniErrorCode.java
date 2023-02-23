package top.cadecode.uniboot.common.exception;

import top.cadecode.uniboot.common.response.ApiStatus;

/**
 * 通用异常错误码接口，使用枚举类继承该类，便于统一管理异常信息
 *
 * @author Cade Li
 * @date 2022/5/8
 */
public interface UniErrorCode {

    String DEFAULT_CODE = "UNKNOWN";
    String DEFAULT_MESSAGE = "未知错误";

    default String getCode() {
        return DEFAULT_CODE;
    }

    default String getMessage() {
        return DEFAULT_MESSAGE;
    }

    default int getStatus() {
        return ApiStatus.SERVER_ERROR;
    }

    /**
     * 未知异常
     */
    UniErrorCode UNKNOWN = new UniErrorCode() {};
}
