package top.cadecode.sa.common.enums;

import lombok.Getter;
import top.cadecode.sa.common.core.response.ResultCode;

/**
 * @author Cade Li
 * @date 2022/2/21
 * @description 基础异常
 */
@Getter
public enum BaseErrorEnum implements ResultCode {

    TOKEN_NOT_EXIST(1000, "未登录"),
    TOKEN_ERROR(1001, "Token 错误"),
    TOKEN_EXPIRED(1002, "Token 已过期"),
    TOKEN_NO_AUTHORITY(1003, "权限不足"),
    TOKEN_CREATE_ERROR(1004, "登录失败"),
    TOKEN_REFRESH_ERROR(1005, "Refresh Token 错误"),
    TOKEN_REFRESH_EXPIRED(1006, "Refresh Token 已过期"),
    REQ_PARAM_INVALID(1007, "请求参数无效"),
    REQ_BODY_INVALID(1008, "请求体无效"),
    REQ_METHOD_INVALID(1009, "请求方法不支持"),
    REQ_PATH_NOT_EXIST(1010, "请求接口不存在"),
    RES_BODY_INVALID(1011, "响应数据为空"),
    ;

    private final String code;
    private final String reason;

    BaseErrorEnum(int index, String reason) {
        this.code = PrefixEnum.BASE.toString() + index;
        this.reason = reason;
    }
}
