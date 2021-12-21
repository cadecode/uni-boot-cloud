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
@ConfigurationProperties("simple.datasource")
public class DataSourceConfig {

    private static final String DBS_PREFIX = "simple.datasource.dbs.";
    private static final String DBS_SUFFIX = ".config";

    private final Environment environment;

    // 注入数据库配置
    private Map<String, DbConfigObject> dbs;

    @Bean
    public DynamicDataSource dynamicDataSource() {
        log.info("配置动态数据源");
        DynamicDataSource dynamicDS = new DynamicDataSource();
        // 获取数据库 key 集合
        Set<String> keys = dbs.keySet();
        // 存储 keys
        DynamicDataSourceHolder.addDataSourceKeys(keys);
        // 定义数据源容器
        Map<Object, Object> dataSourceMap = new HashMap<>();
        // 遍历 dbs
        dbs.forEach((name, db) -> {
            log.info("解析数据源配置 " + name);
            DataSource dataSource = getDataSource(db.getType(), name);
            dataSourceMap.put(name, dataSource);
            // 设置默认数据源
            if (!dynamicDS.hasDefaultDataSource() && db.isDefaultFlag()) {
                dynamicDS.setDefaultTargetDataSource(dataSource);
                log.info("设置默认数据源为 " + name);
            }
        });
        // 判断是否设置默认数据源
        if (!dynamicDS.hasDefaultDataSource()) {
            throw new IllegalArgumentException("未指定默认数据源");
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
            // 判断是否是 DataSource 类型
            if (!DataSource.class.isAssignableFrom(dataSourceClass)) {
                throw new IllegalArgumentException("数据源 " + name + " 的类型 " + type + " 不合适");
            }
            // 生成数据源实例
            return Binder.get(environment)
                    .bind(DBS_PREFIX + name + DBS_SUFFIX, dataSourceClass)
                    .get();
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("数据源 " + name + " 的类型 " + type + " 不存在");
        } catch (Exception e) {
            throw new IllegalArgumentException("创建数据源 " + name + " 时出现绑定错误");
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
