package com.github.cadecode.uniboot.common.plugin.datasource.config;

import com.github.cadecode.uniboot.common.plugin.datasource.dynamic.DynamicDs;
import com.github.cadecode.uniboot.common.plugin.datasource.dynamic.DynamicDsHolder;
import com.github.cadecode.uniboot.common.plugin.datasource.exception.DynamicDsException;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 基于 HikariDataSource 的动态数据源
 *
 * @author Cade Li
 * @date 2022/5/21
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(DynamicDsProperties.class)
@ConditionalOnProperty(name = "uni-boot.dynamic-ds.enable", havingValue = "true")
public class DynamicDsAutoConfig {

    private final DynamicDsProperties properties;

    @Bean
    public DynamicDs dynamicDs() {
        log.info("Checking dynamic datasource config");
        checkDynamicDsConfig();
        log.info("Starting to create dynamic datasource");
        // 创建数据源
        Map<Object, Object> dataSourceMap;
        try {
            dataSourceMap = properties.getDatasource().entrySet().stream()
                    .collect(Collectors.toMap(Entry::getKey, e -> new HikariDataSource(e.getValue())));
        } catch (Exception e) {
            throw new DynamicDsException("Create dynamic datasource fail", e);
        }
        DynamicDs dynamicDs = new DynamicDs();
        // 添加数据源
        dynamicDs.setTargetDataSources(dataSourceMap);
        // 设置默认数据源
        dynamicDs.setDefaultTargetDataSource(dataSourceMap.get(properties.getMaster()));
        DynamicDsHolder.setDataSourceKey(properties.getMaster());
        log.info("Create dynamic datasource over，set default to {}", properties.getMaster());
        return dynamicDs;
    }

    /**
     * 检查是否配置了数据源和 master
     */
    private void checkDynamicDsConfig() {
        // 检查是否配置了数据源
        if (Objects.isNull(properties.getDatasource()) || properties.getDatasource().isEmpty()) {
            throw new DynamicDsException("Dynamic datasource config not found");
        }
        // 检查是否指定主数据源
        if (Objects.isNull(properties.getMaster()) || !properties.getDatasource().containsKey(properties.getMaster())) {
            throw new DynamicDsException("Not found default datasource config");
        }
    }
}
