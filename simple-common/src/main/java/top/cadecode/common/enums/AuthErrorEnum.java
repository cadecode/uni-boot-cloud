package top.cadecode.common.enums;

import lombok.Getter;
import top.cadecode.common.core.response.ResponseCode;

/**
 * @author Cade Li
 * @date 2021/12/9
 * @description 授权异常响应码枚举类
 */
@Getter
public enum AuthErrorEnum implements ResponseCode {

    TOKEN_NOT_EXIST(1000, "未登录"),
    TOKEN_ERROR(1001, "Token 错误"),
    TOKEN_EXPIRED(1002, "Token 已过期"),
    TOKEN_NO_AUTHORITY(1003, "权限不足"),
    TOKEN_CREATE_ERROR(1004, "登录失败"),
    TOKEN_REFRESH_ERROR(1005, "Refresh Token 错误"),
    TOKEN_REFRESH_EXPIRED(1006, "Refresh Token 已过期"),
    ;

    private final String code;
    private final String reason;

    AuthErrorEnum(int index, String reason) {
        this.code = CodePrefixEnum.AUTH.getPrefix() + index;
        this.reason = reason;
    }
}
