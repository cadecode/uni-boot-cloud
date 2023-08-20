package com.github.cadecode.uniboot.common.plugin.mq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 事务消息基本信息
 *
 * @author Cade Li
 * @since 2023/8/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TxMsg implements BaseTxMsg {

    private String id;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 业务键
     */
    private String bizKey;

    private String exchange;

    private String routingKey;

    private String message;
}
