package top.cadecode.common.util;

import java.util.Arrays;

/**
 * @author Cade Li
 * @date 2021/12/4
 * @description 集合工具类
 */
public class CollectUtil {

    /**
     * 判断数组是否为空
     *
     * @param objects 数组
     * @return boolean
     */
    public static boolean isEmpty(Object[] objects) {
        return objects == null || objects.length == 0;
    }

    /**
     * 判断数组是否包含指定元素
     *
     * @param objects 数组
     * @param o       元素
     * @return boolean
     */
    public static boolean contains(Object[] objects, Object o) {
        if (objects == null || objects.length == 0) {
            return false;
        }
        return Arrays.asList(objects).contains(o);
    }


}
