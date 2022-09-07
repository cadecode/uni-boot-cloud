package top.cadecode.sra.common.exception;

import top.cadecode.sra.common.response.ApiStatus;

/**
 * @author Cade Li
 * @date 2022/5/8
 * @description API 错误码接口，使用枚举类继承该类，便于统一管理异常信息
 */
public interface ApiErrorCode {

    default String getCode() {
        return "UNKNOWN";
    }

    default String getMessage() {
        return "未知错误";
    }

    default int getStatus() {
        return ApiStatus.SERVER_ERROR;
    }

    /**
     * 未知异常
     */
    ApiErrorCode UNKNOWN = new ApiErrorCode() {};
}
