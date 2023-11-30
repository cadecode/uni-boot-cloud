package com.github.cadecode.uniboot.common.plugin.storage.handler;

import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.recorder.FileRecorder;
import org.springframework.beans.factory.InitializingBean;

/**
 * 文件存储处理器
 *
 * @author Cade Li
 * @since 2023/10/27
 */
@Slf4j
public abstract class AbstractStorageHandler implements FileRecorder, InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("File storage handler {} is loaded", this.getClass().getName());
    }
}
