package com.github.cadecode.uniboot.common.plugin.mq.consts;

/**
 * RabbitMQ 常量
 *
 * @author Cade Li
 * @since 2023/8/17
 */
public interface RabbitConst {

    // 交换机类型

    String EXC_TYPE_TOPIC = "topic";

    String EXC_TYPE_DIRECT = "direct";

    String EXC_TYPE_FANOUT = "fanout";

    /**
     * delay 交换机类型
     */
    String EXC_TYPE_DELAYED = "x-delayed-message";

    // 交换机类型 --- end

    // 交换机名称
    /**
     * 默认交换机
     */
    String EXC_DEFAULT = "";

    // 交换机名称 --- end
}
