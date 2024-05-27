package com.github.cadecode.uniboot.framework.base.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 角色类型枚举
 *
 * @author Cade Li
 * @since 2024/5/26
 */
@Getter
public enum RoleTypeEnum {

    ACCESS("ACCESS"),
    DATA("DATA"),
    ;

    @EnumValue
    @JsonValue
    private final String prefix;

    RoleTypeEnum(String prefix) {
        this.prefix = prefix;
    }
}
