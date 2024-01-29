package com.github.cadecode.uniboot.framework.base.plugin.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tech.powerjob.worker.annotation.PowerJobHandler;
import tech.powerjob.worker.core.processor.TaskContext;

/**
 * MqTxMsgHandler retry 和 clear 任务适配 powerjob
 *
 * @author Cade Li
 * @since 2024/1/25
 */
@RequiredArgsConstructor
@Component
public class TxMsgJobAdapter {

    private final MqTxMsgHandler mqTxMsgHandler;

    @PowerJobHandler(name = "MqTxMsgRetry")
    public void doRetry(TaskContext taskContext) {
        mqTxMsgHandler.doRetry();
    }

    @PowerJobHandler(name = "MqTxMsgClear")
    public void doClear(TaskContext taskContext) {
        mqTxMsgHandler.doClear();
    }
}
