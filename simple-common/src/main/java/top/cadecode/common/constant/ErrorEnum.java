package top.cadecode.common.constant;

import lombok.Getter;

/**
 * @author Li Jun
 * @date 2021/8/24
 * @description 错误码枚举类
 */
@Getter
public enum ErrorEnum {

    // 未知错误通用码
    UNKNOWN(0, "错误原因未知", StatusEnum.FAIL),

    // 系统框架异常
    TOKEN_NOT_EXIST(1000, "用户未登录", StatusEnum.NO_AUTH),
    TOKEN_ERROR(1001, "用户令牌错误", StatusEnum.NO_AUTH),
    TOKEN_EXPIRED(1002, "用户令牌已过期", StatusEnum.NO_AUTH),
    AUTHORITY_NO(1003, "用户权限不足", StatusEnum.FORBIDDEN),

    // 客户端异常
    API_NOT_EXIST(4004, "请求接口不存在", StatusEnum.NOT_FIT),
    REQ_PARAM_INVALID(4000, "请求参数无效", StatusEnum.NOT_FIT),
    REQ_BODY_INVALID(4001, "请求体无效", StatusEnum.NOT_FIT),
    RES_BODY_INVALID(4002, "响应数据为空", StatusEnum.NOT_FIT),
    REQ_METHOD_INVALID(4003, "请求方法不支持", StatusEnum.NOT_FIT),
    ;

    private final Integer code;
    private final String reason;
    private final StatusEnum statusEnum;

    ErrorEnum(Integer code, String reason, StatusEnum statusEnum) {
        this.code = code;
        this.reason = reason;
        this.statusEnum = statusEnum;
    }
}
