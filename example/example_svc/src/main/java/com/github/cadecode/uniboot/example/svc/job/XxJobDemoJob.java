package com.github.cadecode.uniboot.example.svc.job;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * xxl-job demo
 *
 * @author Cade Li
 * @since 2023/11/10
 */
@Slf4j
@Component
public class XxJobDemoJob {

    @XxlJob("DemoJobHandleTest1")
    public void handleTest1() {
        log.info("XxJobDemoJob handleTest1");
    }

}
