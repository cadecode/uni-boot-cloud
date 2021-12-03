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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
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
            // 配置数据源
            DataSource dataSource = this.setTargetDataSourceMap(key, type);
            targetDataSourceMap.put(key, dataSource);
            // 判断是否是默认数据源
            Boolean isDefault = (Boolean) configMap.get("isDefault");
            this.setDefaultDataSource(dynamicDataSource, isDefault, dataSource, key);
        });
        // 判断是否设置默认数据源
        if (!dynamicDataSource.hasDefaultDataSource()) {
            throw new IllegalArgumentException("未指定默认数据源，使用 isDefault: true 指定");
        }
        // 设置数据源到 dynamicDataSource
        dynamicDataSource.setTargetDataSources(targetDataSourceMap);
        return dynamicDataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
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
     * 设置数据源
     */
    private DataSource setTargetDataSourceMap(String key, String type) {
        Class<DataSource> dataSourceClass;
        try {
            dataSourceClass = (Class<DataSource>) Class.forName(type);
            // 判断是否是 DataSource 类型
            if (!DataSource.class.isAssignableFrom(dataSourceClass)) {
                throw new IllegalArgumentException("数据源 " + key + " 的类型 " + type + " 不合适");
            }
            // 生成数据源实例
            DataSource dataSource = (DataSource) Binder.get(environment)
                    .bind(key + ".config", dataSourceClass).get();
            return dataSource;
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("数据源 " + key + " 的类型 " + type + " 不存在");
        } catch (Exception e) {
            throw new IllegalArgumentException("创建数据源 " + key + " 时出现绑定错误");
        }
    }

    /**
     * 配置默认数据源
     */
    private void setDefaultDataSource(DynamicDataSource dynamicDataSource, Boolean isDefault,
                                      DataSource dataSource, String key) {
        if (dynamicDataSource.hasDefaultDataSource()) {
            return;
        }
        if (isDefault != null && isDefault.equals(true)) {
            dynamicDataSource.setDefaultTargetDataSource(dataSource);
            log.info("设置默认数据源为 " + key);
        }
    }
}
