package top.cadecode.common.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import top.cadecode.common.datasource.DynamicDataSource;
import top.cadecode.common.datasource.DynamicDataSource.DynamicDataSourceHolder;

import javax.sql.DataSource;
import java.util.*;

/**
 * @author Li Jun
 * @date 2021/12/3
 * @description 数据源配置类
 */
@Slf4j
@Configuration
@MapperScan({"top.cadecode.model"})
@RequiredArgsConstructor
public class DataSourceConfig {

    private final Environment environment;

    @Bean
    public DynamicDataSource dynamicDataSource() {
        log.info("配置动态数据源");
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 读取 dataSource.yml
        YamlMapFactoryBean dateSourceMapFactory = new YamlMapFactoryBean();
        dateSourceMapFactory.setResources(new ClassPathResource("dataSource.yml"));
        Map<String, Object> dataSourceMap = dateSourceMapFactory.getObject();
        // 获取所有数据源 key
        Set<String> keys = dataSourceMap.keySet();
        // 存储 keys 到 DynamicDataSourceHolder
        DynamicDataSourceHolder.addDataSourceKeys(keys);
        // 定义数据源容器，用于设置到 dynamicDataSource
        Map<Object, Object> targetDataSourceMap = new HashMap<>();
        // 遍历 keys
        keys.forEach(key -> {
            log.info("解析数据源配置 " + key);
            Map<String, Object> configMap = this.checkDataSourceMap(dataSourceMap, key);
            // 取出数据源类型和 isDefault
            String type = (String) configMap.get("type");
            Boolean isDefault = (Boolean) configMap.get("isDefault");
            // 生成数据源实例
            DataSource dataSource = (DataSource) Binder.get(environment)
                    .bind(key + ".config", this.checkDataSourceType(type, key)).get();
            targetDataSourceMap.put(key, dataSource);
            // 判断是否是默认数据源
            if (isDefault != null && isDefault.equals(true) && !dynamicDataSource.hasDefaultDataSource()) {
                dynamicDataSource.setDefaultTargetDataSource(dataSource);
                log.info("设置默认数据源为 " + key);
            }
        });
        // 判断是否设置默认数据源
        if (!dynamicDataSource.hasDefaultDataSource()) {
            throw new IllegalArgumentException("未指定默认数据源，使用 isDefault: true 指定");
        }
        // 设置数据源到 dynamicDataSource
        dynamicDataSource.setTargetDataSources(targetDataSourceMap);
        return dynamicDataSource;
    }

    /**
     * 校验 dataSourceMap
     */
    private Map<String, Object> checkDataSourceMap(Map<String, Object> dataSourceMap, String key) {
        if (dataSourceMap.get(key) == null || !(dataSourceMap.get(key) instanceof Map)) {
            throw new IllegalArgumentException("数据源 " + key + " 配置有误，无法解析");
        }
        return (Map<String, Object>) dataSourceMap.get(key);
    }

    /**
     * 获取数据源 class
     */
    private Class<?> checkDataSourceType(String type, String key) {
        Class<?> dataSourceClass;
        try {
            dataSourceClass = Class.forName(type);
            // 判断是否是 DataSource 类型
            if (!DataSource.class.isAssignableFrom(dataSourceClass)) {
                throw new IllegalArgumentException("数据源 " + key + " 的类型 " + type + " 不合适");
            }
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("数据源 " + key + " 的类型 " + type + " 不存在");
        }
        return dataSourceClass;
    }
}
