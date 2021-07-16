package info.cadecode.simple.constant;

/**
 * @author Cade Li
 * @date 2021/7/16
 * @description: 自定义响应状态码
 */
public enum Reason {

    OK(200, "OK"),
    BAD_REQ(400, "错误的请求"),
    ERROR(500, "内部服务错误"),


    ;

    Integer code;
    String msg;

    Reason(Integer code, String msg) {
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
