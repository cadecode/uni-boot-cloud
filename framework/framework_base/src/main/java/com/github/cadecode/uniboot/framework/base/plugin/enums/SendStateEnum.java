package com.github.cadecode.uniboot.framework.base.plugin.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 生产者消息发送状态枚举
 *
 * @author Cade Li
 * @since 2023/8/19
 */
@Getter
public enum SendStateEnum {

    /**
     * 处理中
     */
    PREPARING("PREPARING"),

    /**
     * 完成
     */
    OVER("OVER"),

    /**
     * 失败
     */
    FAIL("FAIL"),

    ;

    @EnumValue
    @JsonValue
    private final String state;

    SendStateEnum(String state) {
        this.state = state;
    }
}
