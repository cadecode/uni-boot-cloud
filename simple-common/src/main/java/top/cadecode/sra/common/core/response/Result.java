package top.cadecode.sra.common.core.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Cade Li
 * @date 2021/7/16
 * @description 封装返回响应格式的工具类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    private String code;
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
     * @param data 数据
     * @return Result
     */
    public static <T> Result<T> ok(T data) {
        return Result.of(ResultCode.SUCCESS, data);
    }

    /**
     * 根据 ResultCode 返回响应
     *
     * @param resultCode 响应码
     * @return Result
     */
    public static Result<Object> of(ResultCode resultCode) {
        Result<Object> result = new Result<>();
        result.setCode(resultCode.getCode());
        result.setReason(resultCode.getReason());
        return result;
    }

    /**
     * 根据 ResultCode 和 data 返回响应
     *
     * @param resultCode 错误码
     * @param data    数据
     * @return Result
     */
    public static <T> Result<T> of(ResultCode resultCode, T data) {
        Result<T> result = new Result<>();
        result.setCode(resultCode.getCode());
        result.setReason(resultCode.getReason());
        result.setData(data);
        return result;
    }

    /**
     * 设置 path
     *
     * @param path 请求地址
     * @return Result
     */
    public Result<T> path(String path) {
        this.setPath(path);
        return this;
    }


    /**
     * 设置 data
     *
     * @param errorMsg 附加错误信息
     * @return Result
     */
    public Result<T> errorMsg(String errorMsg) {
        this.setErrorMsg(errorMsg);
        return this;
    }
}
