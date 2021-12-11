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

    JSON_TO_STR_ERROR(1000, "对象转 json 字符串出错"),
    JSON_TO_OBJ_ERROR(1001, "json 字符串转对象出错"),
    JWT_CREATE_ERROR(1003, "创建 token 出错"),
    JWT_VERIFY_ERROR(1004, "解析 token 出错"),
    RES_WRITE_JSON_ERROR(1005, "向响应对象写入 JSON 出错 "),
    ;

    private final String code;
    private final String reason;

    FrameErrorEnum(int index, String reason) {
        this.code = CodePrefixEnum.FRAME.getPrefix() + index;
        this.reason = reason;
    }
}
