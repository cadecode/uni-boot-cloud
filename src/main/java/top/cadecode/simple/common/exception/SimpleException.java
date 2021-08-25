package top.cadecode.simple.common.exception;

import lombok.Getter;
import top.cadecode.simple.constant.ErrorEnum;

/**
 * @author Cade Li
 * @date 2021/7/16
 * @description: 自定义异常类总类
 */
@Getter
public class SimpleException extends RuntimeException {

    private final ErrorEnum errorEnum;

    public SimpleException(ErrorEnum errorEnum) {
        super(errorEnum.getReason());
        this.errorEnum = errorEnum;
    }

    public SimpleException(ErrorEnum errorEnum, String message) {
        super(message);
        this.errorEnum = errorEnum;
    }
}
