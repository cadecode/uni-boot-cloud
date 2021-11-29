package top.cadecode.common.constant;

import lombok.Getter;

/**
 * @author Li Jun
 * @date 2021/8/24
 * @description http 状态码枚举
 */
@Getter
public enum StatusEnum {

    // 成功
    OK(200, "请求成功"),
    OK_ADD(201, "添加成功"),
    OK_DEL(202, "删除成功"),
    OK_UPDATE(203, "更新成功"),

    // 客户端错误
    BAD_REQ(400, "请求错误"),
    NO_AUTH(401, "请求未经认证"),
    FORBIDDEN(403, "请求被拒绝"),
    NOT_EXIT(404, "资源不存在"),
    NOT_FIT(405, "请求方式不合适"),
    TOO_MUCH(429, "请求过于频繁"),

    // 服务端错误
    FAIL(500, "内部服务错误"),
    INVALID_PROXY(502, "代理无效"),
    TEMP_INVALID(503, "服务暂时失效"),
    PROXY_TIME_OUT(504, "代理超时"),
    ;

    private final Integer status;
    private final String message;

    StatusEnum(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
