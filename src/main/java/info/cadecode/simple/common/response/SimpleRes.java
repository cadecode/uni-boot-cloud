package info.cadecode.simple.common.response;

import info.cadecode.simple.constant.Reason;
import info.cadecode.simple.util.JsonUtil;

/**
 * @author Cade Li
 * @date 2021/7/16
 * @description: 封装返回响应格式的工具类
 */
public class SimpleRes {

    private Integer code;
    private String msg;
    private Object data;

    /**
     * 返回执行成功的响应
     *
     * @param data 返回的数据
     * @return ResBuilder
     */
    public static SimpleRes ok(Object data) {
        return new SimpleRes(Reason.OK, data);
    }

    /**
     * 返回执行失败的响应
     *
     * @param data 返回的数据
     * @return ResBuilder
     */
    public static SimpleRes error(Object data) {
        return new SimpleRes(Reason.ERROR, data);
    }

    /**
     * 根据 Reason 绑定 code、msg
     *
     * @param reason Reason
     * @return ResBuilder
     */
    public static SimpleRes reason(Reason reason) {
        return new SimpleRes(reason);
    }

    private SimpleRes(Reason reason) {
        this.code = reason.getCode();
        this.msg = reason.getMsg();
    }

    private SimpleRes(Reason reason, Object data) {
        this.code = reason.getCode();
        this.msg = reason.getMsg();
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    public SimpleRes code(Integer code) {
        this.code = code;
        return this;
    }

    public SimpleRes msg(String msg) {
        this.msg = msg;
        return this;
    }

    public SimpleRes data(Object data) {
        this.data = data;
        return this;
    }

    public String json() {
        return JsonUtil.objToStr(this);
    }
}
