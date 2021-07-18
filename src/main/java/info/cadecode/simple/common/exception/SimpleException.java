package info.cadecode.simple.common.exception;

import info.cadecode.simple.constant.ReasonEnum;

/**
 * @author Cade Li
 * @date 2021/7/16
 * @description: 自定义异常类总类
 */
public class SimpleException extends RuntimeException {


    private final Integer code;
    private final String msg;


    public SimpleException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public SimpleException(ReasonEnum reasonEnum) {
        this.code = reasonEnum.getCode();
        this.msg = reasonEnum.getMsg();
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
