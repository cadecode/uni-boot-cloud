package top.cadecode.sra.common.core.datasource;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Cade Li
 * @date 2021/12/3
 * @description 动态数据源控制器
 */
public class DynamicDataSourceHolder {
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
