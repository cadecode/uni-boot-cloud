package info.cadecode.simple.common.response;

import info.cadecode.simple.constant.Reason;
import info.cadecode.simple.util.JsonUtil;

/**
 * @author Cade Li
 * @date 2021/7/16
 * @description: 封装返回响应格式的工具类
 */
public class SimpleRes {

    /**
     * 返回执行成功的响应
     *
     * @param data 返回的数据
     * @return ResBuilder
     */
    public static ResBuilder ok(Object data) {
        return new ResBuilder(Reason.OK, data);
    }

    /**
     * 返回执行失败的响应
     *
     * @param data 返回的数据
     * @return ResBuilder
     */
    public static ResBuilder error(Object data) {
        return new ResBuilder(Reason.ERROR, data);
    }

    /**
     * 根据 Reason 绑定 code、msg
     *
     * @param reason Reason
     * @return ResBuilder
     */
    public static ResBuilder reason(Reason reason) {
        return new ResBuilder(reason);
    }

    /**
     * 表示响应格式的内部类
     */
    public static class ResBuilder {
        private Integer code;
        private String msg;
        private Object data;

        private ResBuilder(Reason reason) {
            this.code = reason.getCode();
            this.msg = reason.getMsg();
        }

        private ResBuilder(Reason reason, Object data) {
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

        public ResBuilder code(Integer code) {
            this.code = code;
            return this;
        }

        public ResBuilder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public ResBuilder data(Object data) {
            this.data = data;
            return this;
        }

        public String json() {
            return JsonUtil.objToStr(this);
        }
    }
}
