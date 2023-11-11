package com.github.cadecode.uniboot.common.plugin.mq.job;

import cn.hutool.core.util.ObjUtil;
import com.github.cadecode.uniboot.common.plugin.mq.config.TxMsgProperties;
import com.github.cadecode.uniboot.common.plugin.mq.handler.AbstractTxMsgHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

/**
 * 事务消息定时任务
 *
 * @author Cade Li
 * @since 2023/8/19
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class TxMsgJobRegister {

    private final ThreadPoolTaskScheduler taskScheduler;

    private final TxMsgProperties txMsgProperties;

    private final AbstractTxMsgHandler txMsgTaskHandler;

    @EventListener(ApplicationStartedEvent.class)
    public void onApplicationStartedEvent() {
        registerRetryJob();
        registerAutoClearJob();
    }

    private void registerRetryJob() {
        // 消息定时 retry
        log.info("TxMsg task retry start, {}", txMsgProperties.getEnableRetry());
        if (ObjUtil.equals(txMsgProperties.getEnableRetry(), true)) {
            taskScheduler.scheduleWithFixedDelay(txMsgTaskHandler::doRetry, txMsgProperties.getRetryFixDelay());
        }
    }

    private void registerAutoClearJob() {
        // 消息定时清理
        log.info("TxMsg task auto clear start, {}", txMsgProperties.getAutoClear());
        if (ObjUtil.equals(txMsgProperties.getAutoClear(), true)) {
            taskScheduler.scheduleWithFixedDelay(txMsgTaskHandler::doClear, txMsgProperties.getClearFixDelay());
        }
    }
}
