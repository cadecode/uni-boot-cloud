package top.cadecode.common.enums;

import lombok.Getter;
import top.cadecode.common.core.response.ResponseCode;

/**
 * @author Cade Li
 * @date 2021/12/9
 * @description 第三方服务异常响应码枚举类
 */
@Getter
public enum ThirdPartyErrorEnum implements ResponseCode {

    ;

    private final String code;
    private final String reason;

    ThirdPartyErrorEnum(int index, String reason) {
        this.code = CodePrefixEnum.THIRD_PARTY.getPrefix() + index;
        this.reason = reason;
    }
}
