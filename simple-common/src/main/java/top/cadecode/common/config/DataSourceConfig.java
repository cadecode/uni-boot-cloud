package top.cadecode.common.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import top.cadecode.common.config.co.DbsConfigObject;
import top.cadecode.common.datasource.DynamicDataSource;
import top.cadecode.common.datasource.DynamicDataSource.DynamicDataSourceHolder;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author Li Jun
 * @date 2021/12/3
 * @description 数据源配置类
 */
@Slf4j
@Configuration
@ConfigurationProperties("simple.datasource")
@MapperScan({"top.cadecode.model"})
@Data
@RequiredArgsConstructor
public class DataSourceConfig {

    private static final String DBS_PREFIX = "simple.datasource.dbs[";
    private static final String DBS_SUFFIX = "].config";

    private final Environment environment;

    // 注入数据库配置
    private List<DbsConfigObject> dbs;

    @Bean
    public DynamicDataSource dynamicDataSource() {
        log.info("配置动态数据源");
        DynamicDataSource dynamicDS = new DynamicDataSource();
        // 获取数据库 key 集合
        Set<String> keys = this.dbs.stream()
                .map(DbsConfigObject::getName)
                .collect(Collectors.toSet());
        // 存储 keys
        DynamicDataSourceHolder.addDataSourceKeys(keys);
        // 定义数据源容器
        Map<Object, Object> dataSourceMap = new HashMap<>();
        // 遍历 dbs
        AtomicInteger index = new AtomicInteger();
        this.dbs.forEach(db -> {
            log.info("解析数据源配置 " + db.getName());
            DataSource dataSource = this.getDataSource(db.getType(), db.getName(), index.getAndIncrement());
            dataSourceMap.put(db.getName(), dataSource);
            // 设置默认数据源
            if (!dynamicDS.hasDefaultDataSource() && db.isDefaultFlag()) {
                dynamicDS.setDefaultTargetDataSource(dataSource);
                log.info("设置默认数据源为 " + db.getName());
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
    private DataSource getDataSource(String type, String name, int index) {
        Class<DataSource> dataSourceClass;
        try {
            dataSourceClass = (Class<DataSource>) Class.forName(type);
            // 判断是否是 DataSource 类型
            if (!DataSource.class.isAssignableFrom(dataSourceClass)) {
                throw new IllegalArgumentException("数据源 " + name + " 的类型 " + type + " 不合适");
            }
            // 生成数据源实例
            return Binder.get(environment)
                    .bind(DBS_PREFIX + index + DBS_SUFFIX, dataSourceClass).get();
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("数据源 " + name + " 的类型 " + type + " 不存在");
        } catch (Exception e) {
            throw new IllegalArgumentException("创建数据源 " + name + " 时出现绑定错误");
        }
    }
}
