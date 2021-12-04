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
     * @param array 数组
     * @return boolean
     */
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断数组是否包含指定元素
     *
     * @param array 数组
     * @param el    元素
     * @return boolean
     */
    public static <T> boolean contains(T[] array, T el) {
        if (isEmpty(array)) {
            return false;
        }
        return Arrays.asList(array).contains(el);
    }

}
