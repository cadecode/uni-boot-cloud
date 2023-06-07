package top.cadecode.uniboot.common.core.enums.error;

import lombok.Getter;
import top.cadecode.uniboot.common.core.exception.UniErrorCode;
import top.cadecode.uniboot.common.core.response.ApiStatus;

/**
 * 认证授权错误码枚举
 *
 * @author Cade Li
 * @date 2022/5/8
 */
@Getter
public enum AuthErrorEnum implements UniErrorCode {

    TOKEN_NOT_EXIST(0, "未登录") {
        @Override
        public int getStatus() {
            return ApiStatus.NO_AUTHENTICATION;
        }
    },
    TOKEN_ERROR(1, "Token 错误") {
        @Override
        public int getStatus() {
            return ApiStatus.NO_AUTHENTICATION;
        }
    },
    TOKEN_EXPIRED(2, "Token 已过期") {
        @Override
        public int getStatus() {
            return ApiStatus.NO_AUTHENTICATION;
        }
    },
    TOKEN_NO_AUTHORITY(3, "权限不足") {
        @Override
        public int getStatus() {
            return ApiStatus.NO_AUTHORITY;
        }
    },
    TOKEN_CREATE_ERROR(4, "登录失败") {
        @Override
        public int getStatus() {
            return ApiStatus.OK;
        }
    },
    TOKEN_LOGOUT(5, "用户主动注销") {
        @Override
        public int getStatus() {
            return ApiStatus.NO_AUTHENTICATION;
        }
    },
    ;

    private final String code;

    private final String message;

    AuthErrorEnum(int code, String message) {
        this.code = "AUTH_" + code;
        this.message = message;
    }
}
