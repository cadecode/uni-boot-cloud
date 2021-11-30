package top.cadecode.common.exception;

import lombok.Getter;
import top.cadecode.common.constant.ResCode;

/**
 * @author Cade Li
 * @date 2021/7/16
 * @description 自定义异常类总类
 */
@Getter
public class SimpleException extends RuntimeException {

    private final ResCode resCode;

    public SimpleException(ResCode resCode) {
        this.resCode = resCode;
    }

    public SimpleException(ResCode resCode, String errorMsg) {
        super(errorMsg);
        this.resCode = resCode;
    }
}
