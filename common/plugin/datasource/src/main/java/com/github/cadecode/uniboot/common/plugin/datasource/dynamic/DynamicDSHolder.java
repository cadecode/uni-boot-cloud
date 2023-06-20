package com.github.cadecode.uniboot.common.plugin.datasource.dynamic;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * 动态数据源控制器
 *
 * @author Cade Li
 * @date 2021/12/3
 */
public class DynamicDSHolder {

    // 定义容器，存储当前线程的数据源 key，使用 stack 实现嵌套切换
    private static final ThreadLocal<Stack<String>> HOLDER = ThreadLocal.withInitial(Stack::new);
    // 定义容器，存储所有数据源 key
    private static final Set<Object> KEYS = new HashSet<>();

    /**
     * 设置数据源 key
     */
    public static void setDataSourceKey(String key) {
        HOLDER.get().push(key);
    }

    /**
     * 取出数据源 key
     */
    public static String getDataSourceKey() {
        // 栈空时返回 null，自动使用默认数据源
        if (HOLDER.get().isEmpty()) {
            return null;
        }
        return HOLDER.get().peek();
    }

    /**
     * 删除数据源 key
     */
    public static void clearDataSourceKey() {
        if (!HOLDER.get().isEmpty()) {
            HOLDER.get().pop();
        }
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
