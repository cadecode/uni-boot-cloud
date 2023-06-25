package com.github.cadecode.uniboot.framework.enums;

import com.github.cadecode.uniboot.common.core.enums.ExtTypeCode;
import lombok.Getter;

/**
 * Token 校验模式枚举
 *
 * @author Cade Li
 * @date 2022/5/28
 */
@Getter
public enum AuthModelEnum implements ExtTypeCode {

    /**
     * JWT 模式，快过期时下发新 token
     */
    JWT("JWT"),

    /**
     * Redis + Token 模式，访问时刷新过期时间
     */
    REDIS("REDIS");

    private final String code;

    AuthModelEnum(String code) {

        this.code = code;
    }
}
