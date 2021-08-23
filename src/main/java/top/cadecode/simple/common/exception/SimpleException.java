package top.cadecode.simple.common.exception;

import lombok.Getter;
import top.cadecode.simple.constant.ReasonEnum;

/**
 * @author Cade Li
 * @date 2021/7/16
 * @description: 自定义异常类总类
 */
@Getter
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
}
