package top.cadecode.uniboot.framework.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.cadecode.uniboot.common.datasource.DynamicDS;
import top.cadecode.uniboot.common.datasource.DynamicDSHolder;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 基于 HikariDataSource 的动态数据源配置
 *
 * @author Cade Li
 * @date 2022/5/21
 */
@Slf4j
@Data
@RequiredArgsConstructor
@Configuration
@ConfigurationProperties("uni-boot.dynamic-ds")
@ConditionalOnProperty(name = "uni-boot.config.dynamic-ds-on", havingValue = "true")
public class DynamicDSConfig {

    /**
     * 主数据源 KEY
     */
    private String master;

    /**
     * 数据源 MAP
     */
    private Map<String, HikariConfig> datasource;

    @Bean
    public DynamicDS dynamicDS() {
        log.info("Checking dynamic datasource config");
        checkDynamicDSConfig();
        log.info("Starting to create dynamic datasource");
        // 创建数据源
        Map<Object, Object> dataSourceMap;
        try {
            dataSourceMap = datasource.entrySet().stream()
                    .collect(Collectors.toMap(Entry::getKey, e -> new HikariDataSource(e.getValue())));
        } catch (Exception e) {
            throw new RuntimeException("Create dynamic datasource fail", e);
        }
        DynamicDS dynamicDS = new DynamicDS();
        // 添加数据源
        dynamicDS.setTargetDataSources(dataSourceMap);
        // 设置默认数据源
        dynamicDS.setDefaultTargetDataSource(dataSourceMap.get(master));
        DynamicDSHolder.setDataSourceKey(master);
        log.info("Create dynamic datasource over，set default to {}", master);
        return dynamicDS;
    }

    /**
     * 检查是否配置了数据源和 master
     */
    private void checkDynamicDSConfig() {
        // 检查是否配置了数据源
        if (Objects.isNull(datasource) || datasource.isEmpty()) {
            throw new RuntimeException("Dynamic datasource config not found");
        }
        // 检查是否指定主数据源
        if (Objects.isNull(master) || !datasource.containsKey(master)) {
            throw new RuntimeException("Not found default datasource config");
        }
    }
}
