package top.cadecode.simple.constant;

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

    // 认证与权限异常
    NO_TOKEN(1000, "用户未登录", StatusEnum.NO_AUTH),
    TOKEN_ERROR(1001, "用户令牌错误", StatusEnum.NO_AUTH),
    TOKEN_EXPIRED(1002, "用户令牌已过期", StatusEnum.NO_AUTH),
    NO_AUTHORITY(1003, "用户权限不足", StatusEnum.FORBIDDEN),
    // 系统异常

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
