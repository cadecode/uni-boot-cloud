package com.github.cadecode.uniboot.framework.base.config;

import com.github.cadecode.uniboot.common.core.extension.strategy.StrategyService;
import com.github.cadecode.uniboot.framework.base.plugin.handler.StorageRecordHandler;
import org.dromara.x.file.storage.core.recorder.FileRecorder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.plugin.core.config.EnablePluginRegistries;

/**
 * Spring Plugin 插件配置
 *
 * @author Cade Li
 * @since 2023/6/24
 */
// 配置策略模式统一接口
@EnablePluginRegistries({StrategyService.class})
@Configuration
public class PluginConfig {

    @Bean
    public FileRecorder fileRecorder(StorageRecordHandler storageRecordHandler) {
        return storageRecordHandler;
    }
}
