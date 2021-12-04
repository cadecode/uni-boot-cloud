package top.cadecode.common.constant;

import lombok.Getter;

/**
 * @author Li Jun
 * @date 2021/8/24
 * @description 错误码枚举类
 */
@Getter
public enum CodeEnum {

    // 未知错误通用码
    UNKNOWN(0, "未知错误"),
    SUCCESS(1, "请求成功"),

    // 用户权限异常
    TOKEN_NOT_EXIST(1000, "未登录"),
    TOKEN_ERROR(1001, "Token 错误"),
    TOKEN_EXPIRED(1002, "Token 已过期"),
    TOKEN_NO_AUTHORITY(1003, "权限不足"),

    // 请求响应异常
    REQ_PARAM_INVALID(2000, "请求参数无效"),
    REQ_BODY_INVALID(2001, "请求体无效"),
    RES_BODY_INVALID(2002, "响应数据为空"),
    REQ_METHOD_INVALID(2003, "请求方法不支持"),
    REQ_PATH_NOT_EXIST(2004, "请求接口不存在"),
    ;

    private final Integer code;
    private final String reason;

    CodeEnum(Integer code, String reason) {
        this.code = code;
        this.reason = reason;
    }
}
