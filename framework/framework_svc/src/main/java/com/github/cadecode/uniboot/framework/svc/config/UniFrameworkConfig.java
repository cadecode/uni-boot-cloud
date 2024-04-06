package com.github.cadecode.uniboot.framework.svc.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * uni-boot-cloud framework 服务配置
 *
 * @author Cade Li
 * @since 2023/7/28
 */
@Slf4j
@Data
@RequiredArgsConstructor
@Configuration
@ConfigurationProperties("uni-boot.framework")
public class UniFrameworkConfig {

    /**
     * 文件基本路径，以/结尾
     */
    private String fileBasePath;

    /**
     * 可上传下载的扩展文件类型
     */
    private List<String> allowedFileExtensions;
}
