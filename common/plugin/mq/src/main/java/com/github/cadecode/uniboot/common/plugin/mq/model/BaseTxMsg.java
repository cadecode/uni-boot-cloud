package com.github.cadecode.uniboot.common.plugin.mq.model;

/**
 * 事务消息基本信息抽象
 *
 * @author Cade Li
 * @since 2023/8/19
 */
public interface BaseTxMsg {

    String getId();

    String getBizType();

    String getBizKey();

    String getExchange();

    String getRoutingKey();

    String getMessage();

    String getConnectionName();

}
