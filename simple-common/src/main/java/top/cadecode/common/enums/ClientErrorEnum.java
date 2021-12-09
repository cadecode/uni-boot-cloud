package top.cadecode.common.enums;

import lombok.Getter;
import top.cadecode.common.core.response.ResponseCode;

/**
 * @author Cade Li
 * @date 2021/12/9
 * @description 客户端异常响应码枚举类
 */
@Getter
public enum ClientErrorEnum implements ResponseCode {

    REQ_PARAM_INVALID(1000, "请求参数无效"),
    REQ_BODY_INVALID(1001, "请求体无效"),
    REQ_METHOD_INVALID(1002, "请求方法不支持"),
    REQ_PATH_NOT_EXIST(1003, "请求接口不存在"),
    ;

    private final String code;
    private final String reason;

    ClientErrorEnum(int index, String reason) {
        this.code = CodePrefixEnum.CLIENT.getPrefix() + index;
        this.reason = reason;
    }
}
