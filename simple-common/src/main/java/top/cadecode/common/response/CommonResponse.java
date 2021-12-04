package top.cadecode.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.cadecode.common.constant.CodeEnum;

/**
 * @author Cade Li
 * @date 2021/7/16
 * @description 封装返回响应格式的工具类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse<T> {

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
    public static <T> CommonResponse<T> ok(T data) {
        return CommonResponse.of(CodeEnum.SUCCESS, data);
    }

    /**
     * 根据 ResCode 返回响应
     *
     * @param codeEnum
     * @return SimpleRes
     */
    public static CommonResponse<Object> of(CodeEnum codeEnum) {
        CommonResponse<Object> res = new CommonResponse<>();
        res.setCode(codeEnum.getCode());
        res.setReason(codeEnum.getReason());
        return res;
    }

    /**
     * 根据 ResCode 和 data 返回响应
     *
     * @param codeEnum
     * @param data
     * @return SimpleRes
     */
    public static <T> CommonResponse<T> of(CodeEnum codeEnum, T data) {
        CommonResponse<T> res = new CommonResponse<>();
        res.setCode(codeEnum.getCode());
        res.setReason(codeEnum.getReason());
        res.setData(data);
        return res;
    }

    /**
     * 设置 path
     *
     * @param path
     * @return SimpleRes
     */
    public CommonResponse<T> path(String path) {
        this.setPath(path);
        return this;
    }


    /**
     * 设置 data
     *
     * @param errorMsg
     * @return SimpleRes
     */
    public CommonResponse<T> errorMsg(String errorMsg) {
        this.setErrorMsg(errorMsg);
        return this;
    }
}
