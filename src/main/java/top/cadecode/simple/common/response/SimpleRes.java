package top.cadecode.simple.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.cadecode.simple.constant.ErrorEnum;
import top.cadecode.simple.constant.StatusEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Cade Li
 * @date 2021/7/16
 * @description: 封装返回响应格式的工具类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleRes {

    private Integer status;
    private String message;
    private String path;
    @JsonInclude(Include.NON_NULL)
    private Object data;
    @JsonInclude(Include.NON_NULL)
    private ResError error;

    /**
     * 异常信息内部类
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResError {
        private Integer code;
        private String reason;

        public ResError(ErrorEnum errorEnum){
            this.code = errorEnum.getCode();
            this.reason = errorEnum.getReason();
        }
    }

    /**
     * 返回执行成功的响应
     *
     * @param data
     * @return SimpleRes
     */
    public static SimpleRes ok(Object data) {
        SimpleRes res = new SimpleRes();
        res.setStatus(StatusEnum.OK.getStatus());
        res.setMessage(StatusEnum.OK.getMessage());
        res.setData(data);
        return res;
    }

    /**
     * 根据 ErrorEnum 返回执行失败响应
     *
     * @param data
     * @return SimpleRes
     */
    public static SimpleRes fail(ErrorEnum errorEnum) {
        SimpleRes res = new SimpleRes();
        res.setStatus(errorEnum.getStatusEnum().getStatus());
        res.setMessage(errorEnum.getStatusEnum().getMessage());
        res.setError(new ResError(errorEnum));
        return res;
    }

    /**
     * 设置 response 的 http 状态码
     *
     * @param data
     * @return SimpleRes
     */
    public SimpleRes status(HttpServletResponse response) {
        response.setStatus(this.getStatus());
        return this;
    }

    /**
     * 设置 path
     *
     * @param path
     * @return SimpleRes
     */
    public SimpleRes path(String path) {
        this.setPath(path);
        return this;
    }

    /**
     * 根据 request 设置 path
     *
     * @param path
     * @return SimpleRes
     */
    public SimpleRes path(HttpServletRequest request) {
        this.setPath(request.getRequestURI());
        return this;
    }

    /**
     * 设置 data
     *
     * @param data
     * @return SimpleRes
     */
    public SimpleRes data(Object data) {
        this.setData(data);
        return this;
    }
}
