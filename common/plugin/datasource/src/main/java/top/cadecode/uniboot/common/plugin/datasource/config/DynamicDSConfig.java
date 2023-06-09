package top.cadecode.uniboot.common.plugin.datasource.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.cadecode.uniboot.common.plugin.datasource.dynamic.DynamicDS;
import top.cadecode.uniboot.common.plugin.datasource.dynamic.DynamicDSHolder;
import top.cadecode.uniboot.common.plugin.datasource.exception.DynamicDSException;

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
@EnableConfigurationProperties(DynamicDSProperties.class)
@ConditionalOnProperty(name = "uni-boot.config.dynamic-ds-on", havingValue = "true")
public class DynamicDSConfig {

    private final DynamicDSProperties properties;

    @Bean
    public DynamicDS dynamicDS() {
        log.info("Checking dynamic datasource config");
        checkDynamicDSConfig();
        log.info("Starting to create dynamic datasource");
        // 创建数据源
        Map<Object, Object> dataSourceMap;
        try {
            dataSourceMap = properties.getDatasource().entrySet().stream()
                    .collect(Collectors.toMap(Entry::getKey, e -> new HikariDataSource(e.getValue())));
        } catch (Exception e) {
            throw new DynamicDSException("Create dynamic datasource fail", e);
        }
        DynamicDS dynamicDS = new DynamicDS();
        // 添加数据源
        dynamicDS.setTargetDataSources(dataSourceMap);
        // 设置默认数据源
        dynamicDS.setDefaultTargetDataSource(dataSourceMap.get(properties.getMaster()));
        DynamicDSHolder.setDataSourceKey(properties.getMaster());
        log.info("Create dynamic datasource over，set default to {}", properties.getMaster());
        return dynamicDS;
    }

    /**
     * 检查是否配置了数据源和 master
     */
    private void checkDynamicDSConfig() {
        // 检查是否配置了数据源
        if (Objects.isNull(properties.getDatasource()) || properties.getDatasource().isEmpty()) {
            throw new DynamicDSException("Dynamic datasource config not found");
        }
        // 检查是否指定主数据源
        if (Objects.isNull(properties.getMaster()) || !properties.getDatasource().containsKey(properties.getMaster())) {
            throw new DynamicDSException("Not found default datasource config");
        }
    }
}
