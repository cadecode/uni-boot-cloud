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

    // 设置为 null 时不序列化
    @JsonInclude(Include.NON_NULL)
    private T data;
    @JsonInclude(Include.NON_NULL)
    private String errorMsg;

    /**
     * 根据 data 返回成功的响应
     *
     * @param data
     * @return SimpleRes
     */
    public static <T> SimpleRes<T> ok(T data) {
        return SimpleRes.of(ResCode.SUCCESS, data);
    }

    /**
     * 根据 ResCode 返回响应
     *
     * @param resCode
     * @return SimpleRes
     */
    public static SimpleRes<Object> of(ResCode resCode) {
        SimpleRes<Object> res = new SimpleRes<>();
        res.setCode(resCode.getCode());
        res.setReason(resCode.getReason());
        return res;
    }

    /**
     * 根据 ResCode 和 data 返回响应
     *
     * @param resCode
     * @param data
     * @return SimpleRes
     */
    public static <T> SimpleRes<T> of(ResCode resCode, T data) {
        SimpleRes<T> res = new SimpleRes<>();
        res.setCode(resCode.getCode());
        res.setReason(resCode.getReason());
        res.setData(data);
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
     * @param errorMsg
     * @return SimpleRes
     */
    public SimpleRes<T> errorMsg(String errorMsg) {
        this.setErrorMsg(errorMsg);
        return this;
    }
}
