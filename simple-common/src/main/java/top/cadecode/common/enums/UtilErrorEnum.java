package top.cadecode.common.enums;

import lombok.Getter;
import top.cadecode.common.core.response.ResultCode;

/**
 * @author Cade Li
 * @date 2022/2/21
 * @description 工具类异常
 */
@Getter
public enum UtilErrorEnum implements ResultCode {

    JSON_TO_STR_ERROR(1000, "对象转 json 字符串出错"),
    JSON_TO_OBJ_ERROR(1001, "json 字符串转对象出错"),
    JWT_CREATE_ERROR(1002, "创建 token 出错"),
    JWT_VERIFY_ERROR(1003, "解析 token 出错"),
    WRITE_JSON_RES_ERROR(1004, "向响应对象写入 json 出错 "),
    ;

    private final String code;
    private final String reason;

    UtilErrorEnum(int index, String reason) {
        this.code = PrefixEnum.UTIL.toString() + index;
        this.reason = reason;
    }
}
