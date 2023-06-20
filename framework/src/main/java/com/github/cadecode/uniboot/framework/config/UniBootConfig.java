package com.github.cadecode.uniboot.framework.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * uni-boot-admin 主配置类，维护一些功能开关和全局变量
 *
 * @author Cade Li
 * @date 2022/5/21
 */
@Slf4j
@Data
@RequiredArgsConstructor
@Configuration
@ConfigurationProperties("uni-boot.config")
public class UniBootConfig {

    /**
     * 应用名称
     */
    private String name = "uni-boot-admin";

    /**
     * 版本号
     */
    private String version;

    /**
     * 是否开启 swagger 文档
     */
    private boolean swaggerOn;

    /**
     * 是否开启动态数据源配置
     */
    private boolean dynamicDsOn;

    /**
     * 文件基本路径，以/结尾
     */
    private String fileBasePath;

    /**
     * 可上传下载的扩展文件类型
     */
    private List<String> allowedFileExtensions;

}
