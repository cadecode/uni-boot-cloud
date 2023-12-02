package com.github.cadecode.uniboot.common.plugin.mq.util;

import com.github.cadecode.uniboot.common.plugin.mq.config.TxMsgProperties;
import com.github.cadecode.uniboot.common.plugin.mq.config.TxMsgProperties.MsgOption;
import com.github.cadecode.uniboot.common.plugin.mq.handler.AbstractTxMsgHandler;
import com.github.cadecode.uniboot.common.plugin.mq.model.BaseTxMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 事务消息工具类
 *
 * @author Cade Li
 * @since 2023/8/19
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class TxMsgKit {

    private final TxMsgProperties txMsgProperties;

    private final AbstractTxMsgHandler txMsgTaskHandler;

    /**
     * 发送事务消息
     * 需要事务
     *
     * @param txMsg 事务消息
     */
    public void sendTx(BaseTxMsg txMsg) {
        sendTx(txMsg, null);
    }

    /**
     * 发送事务消息
     * 需要事务
     *
     * @param txMsg 事务消息
     * @param msgOption 消息配置
     */
    public void sendTx(BaseTxMsg txMsg, MsgOption msgOption) {
        MsgOption currOption = txMsgProperties.createMsgOption(msgOption);
        txMsgTaskHandler.checkBeforeSend(txMsg, currOption);
        // 若没有事务
        if (!TransactionSynchronizationManager.isActualTransactionActive()) {
            log.error("TxMsg send need transaction, txMsg:{}, biz:{}_{}", txMsg.getId(), txMsg.getBizType(), txMsg.getBizKey());
            txMsgTaskHandler.sendNoTransaction(txMsg, currOption);
            return;
        }
        // 持久化
        txMsgTaskHandler.saveBeforeRegister(txMsg, currOption);
        // 注册到事务管理器
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCompletion(int status) {
                // 判断状态
                if (status != TransactionSynchronization.STATUS_COMMITTED) {
                    log.error("TxMsg send not committed, txMsg:{}, biz:{}_{}", txMsg.getId(), txMsg.getBizType(), txMsg.getBizKey());
                    txMsgTaskHandler.sendNotCommit(txMsg, currOption);
                    return;
                }
                txMsgTaskHandler.sendCommitted(txMsg, currOption);
            }
        });
    }

    /**
     * 发送事务消息
     * 不检查是否存在事务
     *
     * @param txMsg 事务消息
     * @param msgOption 消息配置
     */
    public void sendWithoutTx(BaseTxMsg txMsg, MsgOption msgOption) {
        MsgOption currOption = txMsgProperties.createMsgOption(msgOption);
        txMsgTaskHandler.checkBeforeSend(txMsg, currOption);
        txMsgTaskHandler.saveBeforeRegister(txMsg, currOption);
        txMsgTaskHandler.sendCommitted(txMsg, currOption);
    }
}
