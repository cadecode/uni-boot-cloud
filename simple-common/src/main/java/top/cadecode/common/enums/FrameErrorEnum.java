package top.cadecode.common.enums;

import lombok.Getter;
import top.cadecode.common.core.response.ResponseCode;

/**
 * @author Cade Li
 * @date 2021/12/9
 * @description 系统框架异常
 */
@Getter
public enum FrameErrorEnum implements ResponseCode {

    JSON_TO_STR_ERROR(1000, "对象转 json 字符串失败"),
    JSON_TO_OBJ_ERROR(1000, "json 字符串转对象失败"),
    ;

    private final String code;
    private final String reason;

    FrameErrorEnum(int index, String reason) {
        this.code = CodePrefixEnum.FRAME.getPrefix() + index;
        this.reason = reason;
    }
}
