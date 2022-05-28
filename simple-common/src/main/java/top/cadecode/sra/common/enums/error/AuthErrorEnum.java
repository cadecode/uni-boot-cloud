package top.cadecode.sra.common.enums.error;

import lombok.Getter;
import top.cadecode.sra.common.exception.ApiErrorCode;

/**
 * @author Cade Li
 * @date 2022/5/8
 * @description 认证授权错误码枚举
 */
@Getter
public enum AuthErrorEnum implements ApiErrorCode {

    TOKEN_NOT_EXIST(0, "未登录"),

    TOKEN_ERROR(1, "Token 错误"),

    TOKEN_EXPIRED(2, "Token 已过期"),

    TOKEN_NO_AUTHORITY(3, "权限不足"),

    TOKEN_CREATE_ERROR(4, "登录失败"),

    TOKEN_REFRESH_ERROR(5, "Refresh Token 错误"),

    TOKEN_REFRESH_EXPIRED(6, "Refresh Token 已过期"),
    ;

    private final String code;

    private final String message;

    AuthErrorEnum(int code, String message) {
        this.code = "AUTH_" + code;
        this.message = message;
    }
}
