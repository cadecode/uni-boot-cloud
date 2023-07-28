package com.github.cadecode.uniboot.framework.api.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * uni-boot-admin 通用配置类
 * 维护一些功能开关和全局变量
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
    private String name;

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
}
