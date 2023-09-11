package com.github.cadecode.uniboot.common.plugin.mq.task;

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
public class TxMsgTask {

    private final ThreadPoolTaskScheduler taskScheduler;

    private final TxMsgProperties txMsgProperties;

    private final AbstractTxMsgHandler txMsgTaskHandler;

    @EventListener(ApplicationStartedEvent.class)
    public void onApplicationStartedEvent() {
        if (ObjUtil.equals(txMsgProperties.getEnableRetry(), false)) {
            log.info("TxMsg task enable retry false");
            return;
        }
        log.info("TxMsg task do retry started");
        taskScheduler.scheduleWithFixedDelay(txMsgTaskHandler::doRetry, txMsgProperties.getRetryFixDelay());
        log.info("TxMsg task do clear started, {}", txMsgProperties.getAutoClear());
        if (ObjUtil.equal(txMsgProperties.getAutoClear(), true)) {
            taskScheduler.scheduleWithFixedDelay(() -> txMsgTaskHandler.doClear(txMsgProperties.getAutoClearInterval()),
                    txMsgProperties.getClearFixDelay());
        }
    }
}
