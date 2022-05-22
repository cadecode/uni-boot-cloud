package top.cadecode.sra.framework.config.core;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.cadecode.sra.common.datasource.DynamicDS;
import top.cadecode.sra.common.datasource.DynamicDSHolder;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Cade Li
 * @date 2022/5/21
 * @description 基于 HikariDataSource 的动态数据源配置
 */
@Slf4j
@Data
@RequiredArgsConstructor
@Configuration
@ConfigurationProperties("sra.dynamic-ds")
@ConditionalOnProperty(name = "sra.config.dynamic-ds-on", havingValue = "true")
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
        log.info("检查动态数据源配置");
        checkDynamicDSConfig();
        log.info("开始创建动态数据源");
        // 创建数据源
        Map<Object, Object> dataSourceMap;
        try {
            dataSourceMap = datasource.entrySet().stream()
                    .collect(Collectors.toMap(Entry::getKey, e -> new HikariDataSource(e.getValue())));
        } catch (Exception e) {
            throw new RuntimeException("创建数据源失败", e);
        }
        DynamicDS dynamicDS = new DynamicDS();
        // 添加数据源
        dynamicDS.setTargetDataSources(dataSourceMap);
        // 设置默认数据源
        dynamicDS.setDefaultTargetDataSource(dataSourceMap.get(master));
        DynamicDSHolder.setDataSourceKey(master);
        log.info("创建动态数据源完成，默认数据源设置为 {}", master);
        return dynamicDS;
    }

    /**
     * 检查是否配置了数据源和 master
     */
    private void checkDynamicDSConfig() {
        // 检查是否配置了数据源
        if (Objects.isNull(datasource) || datasource.isEmpty()) {
            throw new RuntimeException("找不到数据源配置");
        }
        // 检查是否指定主数据源
        if (Objects.isNull(master) || !datasource.containsKey(master)) {
            throw new RuntimeException("没有指定主数据源或主数据源不在配置中");
        }
    }
}
