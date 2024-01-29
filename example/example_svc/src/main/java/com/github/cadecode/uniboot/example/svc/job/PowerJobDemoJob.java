package com.github.cadecode.uniboot.example.svc.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tech.powerjob.worker.annotation.PowerJobHandler;
import tech.powerjob.worker.core.processor.ProcessResult;
import tech.powerjob.worker.core.processor.TaskContext;
import tech.powerjob.worker.core.processor.sdk.BasicProcessor;

/**
 * powerjob demo
 *
 * @author Cade Li
 * @since 2023/11/10
 */
@Slf4j
@Component
public class PowerJobDemoJob implements BasicProcessor {

    @Override
    public ProcessResult process(TaskContext taskContext) {
        taskContext.getOmsLogger().info("PowerJobDemoJob process");
        return new ProcessResult(true, "PowerJobDemoJob OK");
    }

    @PowerJobHandler(name = "PowerJobDemoJobTestAnno")
    public void testAnno(TaskContext taskContext) {
        log.info("PowerJobDemoJobTestAnno OK");
    }
}
