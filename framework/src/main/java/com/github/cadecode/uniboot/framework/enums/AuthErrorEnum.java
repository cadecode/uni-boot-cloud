package com.github.cadecode.uniboot.framework.enums;

import com.github.cadecode.uniboot.common.core.enums.ApiErrorCode;
import com.github.cadecode.uniboot.common.core.web.response.ApiStatus;
import lombok.Getter;

/**
 * 认证授权错误码枚举
 *
 * @author Cade Li
 * @date 2022/5/8
 */
@Getter
public enum AuthErrorEnum implements ApiErrorCode {

    TOKEN_NOT_EXIST(1000, "未登录") {
        @Override
        public int getStatus() {
            return ApiStatus.NO_AUTHENTICATION;
        }
    },
    TOKEN_ERROR(1001, "Token 错误") {
        @Override
        public int getStatus() {
            return ApiStatus.NO_AUTHENTICATION;
        }
    },
    TOKEN_EXPIRED(1002, "Token 已过期") {
        @Override
        public int getStatus() {
            return ApiStatus.NO_AUTHENTICATION;
        }
    },
    TOKEN_NO_AUTHORITY(1003, "权限不足") {
        @Override
        public int getStatus() {
            return ApiStatus.NO_AUTHORITY;
        }
    },
    TOKEN_CREATE_ERROR(1004, "登录失败") {
        @Override
        public int getStatus() {
            return ApiStatus.OK;
        }
    },
    TOKEN_LOGOUT(1005, "用户主动注销") {
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
