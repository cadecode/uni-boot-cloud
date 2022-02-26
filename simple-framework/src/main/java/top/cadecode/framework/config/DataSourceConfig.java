package top.cadecode.framework.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import top.cadecode.common.core.datasource.DynamicDataSource;
import top.cadecode.common.core.datasource.DynamicDataSourceHolder;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Cade Li
 * @date 2021/12/3
 * @description 数据源配置类
 */
@Slf4j
@Data
@RequiredArgsConstructor
@Configuration
@ConfigurationProperties("custom")
public class DataSourceConfig {

    private static final String DBS_PREFIX = "custom.dbs.";
    private static final String DBS_SUFFIX = ".config";

    private final Environment environment;

    /**
     * 注入数据库配置
     */
    private Map<String, DbConfigObject> dbs;

    @Bean
    public DynamicDataSource dynamicDataSource() {
        log.info("Starting configuring dynamic data source ");
        DynamicDataSource dynamicDS = new DynamicDataSource();
        // 获取数据库 key 集合
        Set<String> keys = dbs.keySet();
        // 存储 keys
        DynamicDataSourceHolder.addDataSourceKeys(keys);
        // 定义数据源容器
        Map<Object, Object> dataSourceMap = new HashMap<>();
        // 遍历 dbs
        dbs.forEach((name, db) -> {
            log.info("Parsing data source configuration of" + name);
            DataSource dataSource = getDataSource(db.getType(), name);
            dataSourceMap.put(name, dataSource);
            // 设置默认数据源
            if (!dynamicDS.hasDefaultDataSource() && db.isDefaultFlag()) {
                dynamicDS.setDefaultTargetDataSource(dataSource);
                log.info("Setting default data source to" + name);
            }
        });
        // 判断是否设置默认数据源
        if (!dynamicDS.hasDefaultDataSource()) {
            throw new IllegalArgumentException("No default data source specified");
        }
        // 设置数据源到 dynamicDataSource
        dynamicDS.setTargetDataSources(dataSourceMap);
        return dynamicDS;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }

    /**
     * 获取数据源实例工具方法
     */
    @SuppressWarnings("unchecked")
    private DataSource getDataSource(String type, String name) {
        Class<DataSource> dataSourceClass;
        try {
            dataSourceClass = (Class<DataSource>) Class.forName(type);
            // if: 不是DataSource 类型
            if (!DataSource.class.isAssignableFrom(dataSourceClass)) {
                throw new IllegalArgumentException("The type " + type + " of data source " + name + " is illegal");
            }
            // 生成数据源实例
            return Binder.get(environment)
                    .bind(DBS_PREFIX + name + DBS_SUFFIX, dataSourceClass)
                    .get();
        } catch (Exception e) {
            IllegalArgumentException ie = new IllegalArgumentException("Binding error occurred when configure data source " + name);
            ie.addSuppressed(e);
            throw ie;
        }
    }

    /**
     * 数据库配置对象类
     */
    @Data
    public static class DbConfigObject {
        private String type;
        private boolean defaultFlag;
    }
}
