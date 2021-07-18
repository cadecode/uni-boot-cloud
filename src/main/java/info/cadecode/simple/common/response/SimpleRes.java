package info.cadecode.simple.common.response;

import info.cadecode.simple.common.exception.SimpleException;
import info.cadecode.simple.constant.ReasonEnum;
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

    private SimpleRes(ReasonEnum reasonEnum) {
        this.code = reasonEnum.getCode();
        this.msg = reasonEnum.getMsg();
    }


    private SimpleRes(SimpleException exception) {
        this.code = exception.getCode();
        this.msg = exception.getMsg();
    }

    /**
     * 根据 Reason 绑定 code、msg
     *
     * @param reasonEnum Reason
     * @return ResBuilder
     */
    public static SimpleRes reason(ReasonEnum reasonEnum) {
        return new SimpleRes(reasonEnum);
    }


    /**
     * 根据 SimpleException 绑定 code、msg
     *
     * @param exception Reason
     * @return ResBuilder
     */
    public static SimpleRes exception(SimpleException exception) {
        return new SimpleRes(exception);
    }

    /**
     * 返回执行成功的响应 200
     *
     * @param data 返回的数据
     * @return ResBuilder
     */
    public static SimpleRes ok(Object data) {
        return reason(ReasonEnum.OK).data(data);
    }

    /**
     * 返回执行失败的响应 500
     *
     * @param data 返回的数据
     * @return ResBuilder
     */
    public static SimpleRes error(Object data) {
        return reason(ReasonEnum.ERROR).data(data);
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

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }
}
