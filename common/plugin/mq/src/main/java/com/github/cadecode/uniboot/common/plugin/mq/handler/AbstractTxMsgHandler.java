package com.github.cadecode.uniboot.common.plugin.mq.handler;

import com.github.cadecode.uniboot.common.plugin.mq.config.TxMsgProperties.MsgOption;
import com.github.cadecode.uniboot.common.plugin.mq.model.BaseTxMsg;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;

/**
 * 事务消息处理器
 * 1. 入库/更新
 * 2. 重试/清理记录
 *
 * @author Cade Li
 * @since 2023/8/20
 */
public abstract class AbstractTxMsgHandler {

    public abstract void doRetry();

    public abstract void doClear(Long autoClearInterval);

    public abstract void checkBeforeSend(BaseTxMsg txMsg, MsgOption msgOption);

    public abstract void sendNoTransaction(BaseTxMsg txMsg, MsgOption msgOption);

    public abstract void sendNotCommit(BaseTxMsg txMsg, MsgOption msgOption);

    public abstract void saveBeforeRegister(BaseTxMsg txMsg, MsgOption msgOption);

    public abstract void sendCommitted(BaseTxMsg txMsg, MsgOption msgOption);

    public abstract void handleConfirm(CorrelationData correlationData, boolean ack, String cause);

    public abstract void handleReturned(ReturnedMessage returned);

}
