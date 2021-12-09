package top.cadecode.common.enums;

import lombok.Getter;
import top.cadecode.common.core.response.ResponseCode;

/**
 * @author Cade Li
 * @date 2021/12/9
 * @description 服务异常响应码枚举类
 */
@Getter
public enum ServiceErrorEnum implements ResponseCode {

    RES_BODY_INVALID(10000, "响应数据为空"),
    ;

    private final String code;
    private final String reason;

    ServiceErrorEnum(int index, String reason) {
        this.code = CodePrefixEnum.SERVICE.getPrefix() + index;
        this.reason = reason;
    }
}
