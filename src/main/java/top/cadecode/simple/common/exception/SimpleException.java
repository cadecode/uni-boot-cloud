package top.cadecode.simple.common.exception;

import lombok.Data;
import lombok.Getter;
import top.cadecode.simple.constant.ErrorEnum;
import top.cadecode.simple.constant.ReasonEnum;

/**
 * @author Cade Li
 * @date 2021/7/16
 * @description: 自定义异常类总类
 */
@Getter
public class SimpleException extends RuntimeException {

    private final ErrorEnum errorEnum;

    public SimpleException(ErrorEnum errorEnum, String message) {
        super(message);
        this.errorEnum = errorEnum;
    }
}
