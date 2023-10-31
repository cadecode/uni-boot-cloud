package com.github.cadecode.uniboot.common.plugin.storage.config;

import com.github.cadecode.uniboot.common.plugin.storage.handler.AbstractStorageHandler;
import lombok.RequiredArgsConstructor;
import org.dromara.x.file.storage.core.recorder.FileRecorder;
import org.dromara.x.file.storage.spring.EnableFileStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * x-file-storage 配置类
 *
 * @author Cade Li
 * @since 2023/10/25
 */
@EnableFileStorage
@RequiredArgsConstructor
@Configuration
public class XFileStorageConfig {

    @Bean
    public FileRecorder fileRecorder(AbstractStorageHandler storageRecordHandler) {
        return storageRecordHandler;
    }
}
