package top.cadecode.simple.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import top.cadecode.simple.common.exception.SimpleException;
import top.cadecode.simple.constant.ReasonEnum;
import top.cadecode.simple.util.JsonUtil;
import top.cadecode.simple.util.MapUtil;

import java.util.Map;

/**
 * @author Cade Li
 * @date 2021/7/16
 * @description: 封装返回响应格式的工具类
 */
@Getter
public class SimpleRes {

    private Integer code;
    private String msg;
    private String path;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;

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
    public static SimpleRes fail(Object data) {
        return reason(ReasonEnum.FAIL).data(data);
    }

    public SimpleRes code(Integer code) {
        this.code = code;
        return this;
    }

    public SimpleRes msg(String msg) {
        this.msg = msg;
        return this;
    }

    public SimpleRes path(String path) {
        this.path = path;
        return this;
    }

    public SimpleRes data(Object data) {
        this.data = data;
        return this;
    }

    public SimpleRes error(String error) {
        this.error = error;
        return this;
    }

    public String json() {
        return JsonUtil.objToStr(this);
    }

    public Map<String, Object> map() {
        Map<String, Object> map = MapUtil.create()
                .add("code", code)
                .add("msg", msg)
                .add("path", path)
                .asMap();
        if (data != null) {
            map.put("data", data);
        }
        if (error != null) {
            map.put("error", error);
        }
        return map;
    }
}
