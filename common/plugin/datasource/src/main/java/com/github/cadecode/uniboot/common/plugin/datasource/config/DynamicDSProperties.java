package com.github.cadecode.uniboot.common.plugin.datasource.config;

import com.zaxxer.hikari.HikariConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * HikariDataSource 的动态数据源配置
 *
 * @author Cade Li
 * @since 2023/6/8
 */
@Data
@ConfigurationProperties("uni-boot.dynamic-ds")
public class DynamicDSProperties {

    /**
     * 主数据源 KEY
     */
    private String master;

    /**
     * 数据源 MAP
     */
    private Map<String, HikariConfig> datasource;

}
