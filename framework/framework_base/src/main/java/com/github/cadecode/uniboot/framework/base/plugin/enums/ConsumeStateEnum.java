package com.github.cadecode.uniboot.framework.base.plugin.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 消费者消息确认状态枚举
 *
 * @author Cade Li
 * @since 2023/8/19
 */
@Getter
public enum ConsumeStateEnum {

    /**
     * 确认
     */
    ACK("ACK"),

    /**
     * 不确认
     */
    NACK("NACK"),

    ;

    @EnumValue
    @JsonValue
    private final String state;

    ConsumeStateEnum(String state) {
        this.state = state;
    }
}
