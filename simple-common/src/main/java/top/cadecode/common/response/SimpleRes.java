package top.cadecode.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.cadecode.common.constant.ResCode;

/**
 * @author Cade Li
 * @date 2021/7/16
 * @description 封装返回响应格式的工具类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleRes<T> {

    private Integer code;
    private String reason;
    private String path;
    @JsonInclude(Include.NON_NULL)
    private T data;
    @JsonInclude(Include.NON_NULL)
    private String errorMsg;

    /**
     * 返回成功的响应
     *
     * @param data
     * @return SimpleRes
     */
    public static <T> SimpleRes<T> ok(T data) {
        SimpleRes<T> res = new SimpleRes<>();
        ResCode success = ResCode.SUCCESS;
        res.setCode(success.getCode());
        res.setReason(success.getReason());
        res.setData(data);
        return res;

    }

    /**
     * 根据 ResCode 返回响应
     *
     * @param resCode
     * @return SimpleRes
     */
    public static SimpleRes<?> of(ResCode resCode) {
        SimpleRes<?> res = new SimpleRes<>();
        res.setCode(resCode.getCode());
        res.setReason(resCode.getReason());
        return res;
    }

    /**
     * 设置 path
     *
     * @param path
     * @return SimpleRes
     */
    public SimpleRes<T> path(String path) {
        this.setPath(path);
        return this;
    }

    /**
     * 设置 data
     *
     * @param data
     * @return SimpleRes
     */
    public SimpleRes<T> data(T data) {
        this.setData(data);
        return this;
    }

    /**
     * 设置 data
     *
     * @param errorMsg
     * @return SimpleRes
     */
    public SimpleRes<T> errorMsg(String errorMsg) {
        this.setErrorMsg(errorMsg);
        return this;
    }
}
