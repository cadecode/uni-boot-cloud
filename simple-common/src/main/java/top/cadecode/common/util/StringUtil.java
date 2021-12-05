package top.cadecode.common.util;

import lombok.NonNull;

/**
 * @author Cade Li
 * @date 2021/7/15
 * @description 字符串工具类
 */
public class StringUtil {

    /**
     * 判断字符串是否有内容（包括空格）
     *
     * @return boolean
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 判断字符串是否有内容（不包括前后空格）
     *
     * @return boolean
     */
    public static boolean isTrimEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 判断字符串是否包含指定子串
     *
     * @return boolean
     */
    public static boolean contains(String str, String subStr) {
        if (str == null || subStr == null) {
            return false;
        }
        return str.contains(subStr);
    }

    /**
     * 截取字符串，支持反序负数
     *
     * @param str   字符串
     * @param begin 开始位置
     * @return String
     */
    public static String subString(String str, int begin) {
        return subString(str, begin, str.length());
    }

    /**
     * 截取字符串，支持反序负数
     *
     * @param str   字符串
     * @param begin 开始位置
     * @param end   开始位置
     * @return String
     */
    public static String subString(String str, int begin, int end) {
        if (str == null) {
            return null;
        }
        if (begin < 0) {
            begin = str.length() + begin;
        }
        if (end < 0) {
            end = str.length() + end;
        }
        return str.substring(begin, end);
    }

}
