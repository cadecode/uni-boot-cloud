package top.cadecode.common.exception;

import lombok.Getter;
import top.cadecode.common.constant.CodeEnum;

/**
 * @author Cade Li
 * @date 2021/7/16
 * @description 自定义异常类总类
 */
@Getter
public class CommonException extends RuntimeException {

    private final CodeEnum codeEnum;

    public CommonException(CodeEnum codeEnum) {
        this.codeEnum = codeEnum;
    }

    public CommonException(CodeEnum codeEnum, String errorMsg) {
        super(errorMsg);
        this.codeEnum = codeEnum;
    }
}
