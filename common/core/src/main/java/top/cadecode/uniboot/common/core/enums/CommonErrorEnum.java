package top.cadecode.uniboot.common.core.enums;

import lombok.Getter;
import top.cadecode.uniboot.common.core.exception.UniErrorCode;

/**
 * Common 模块错误码枚举
 *
 * @author Cade Li
 * @date 2022/5/8
 */
@Getter
public enum CommonErrorEnum implements UniErrorCode {

    // json
    CAST_BEAN_TO_JSON_FAIL(0, "cast bean to json fail"),
    CAST_JSON_TO_BEAN_FAIL(0, "cast json to bean fail"),
    ;

    private final String code;

    private final String message;

    CommonErrorEnum(int code, String message) {
        this.code = "COMMON_" + code;
        this.message = message;
    }
}
