package top.cadecode.simple.constant;

/**
 * @author Cade Li
 * @date 2021/7/16
 * @description: 自定义响应状态码
 */
public enum ReasonEnum {

    // 200 成功
    OK(200, "OK"),
    OK_ADD(201, "Add success"),
    OK_DEL(202, "Delete success"),
    OK_UPDATE(203, "Update success"),

    // 400 客户端错误
    BAD_REQ(400, "错误的请求"),
    NO_AUTH(401, "未经验证的请求"),
    FORBIDDEN(403, "被拒绝的请求"),
    NOT_EXIT(404, "不存在的资源"),
    NOT_FIT(405, "不支持的请求方式"),
    TOO_MUCH(429, "过于频繁的请求"),

    // 500 服务端错误
    FAIL(500, "内部服务错误"),
    INVALID_PROXY(502, "无效代理"),
    TEMP_INVALID(503, "服务暂时失效"),
    PROXY_TIME_OUT(504, "代理超时"),

    // 自定义异常 Reason

    ;

    Integer code;
    String msg;

    ReasonEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
