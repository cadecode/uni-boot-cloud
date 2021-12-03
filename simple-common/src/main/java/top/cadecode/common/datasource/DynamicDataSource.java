package top.cadecode.common.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Li Jun
 * @date 2021/12/3
 * @description 动态数据源类
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private boolean hasDefaultDataSource;

    /**
     * 设置数据源 key
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDataSourceKey();
    }

    /**
     * 设置全部数据源
     */
    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        // 存储数据源 key
        DynamicDataSourceHolder.addDataSourceKeys(targetDataSources.keySet());
    }

    /**
     * 设置默认数据源
     */
    @Override
    public void setDefaultTargetDataSource(Object defaultTargetDataSource) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        this.hasDefaultDataSource = true;
    }

    /**
     * 获取是否设置默认数据源
     */
    public boolean hasDefaultDataSource() {
        return this.hasDefaultDataSource;
    }

    /**
     * 切换数据源
     */
    @Override
    protected DataSource determineTargetDataSource() {
        return super.determineTargetDataSource();
    }

    /**
     * 动态数据源控制器
     */
    public static class DynamicDataSourceHolder {
        // 定义容器，存储当前线程的数据源 key
        private static final ThreadLocal<String> HOLDER = new ThreadLocal<>();
        // 定义容器，存储所有数据源 key
        private static final Set<Object> KEYS = new HashSet<>();

        /**
         * 设置数据源 key
         */
        public static void setDataSourceKey(String key) {
            HOLDER.set(key);
        }

        /**
         * 取出数据源 key
         */
        public static String getDataSourceKey() {
            return HOLDER.get();
        }

        /**
         * 重置数据源 key
         */
        public static void clearDataSourceKey() {
            HOLDER.remove();
        }

        /**
         * 判断是否包含数据源
         */
        public static boolean containDataSourceKey(String key) {
            return KEYS.contains(key);
        }

        /**
         * 添加数据源 key
         */
        public static void addDataSourceKeys(Set<?> keys) {
            KEYS.addAll(keys);
        }
    }
}
