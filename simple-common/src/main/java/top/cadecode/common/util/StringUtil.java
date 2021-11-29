package top.cadecode.common.util;

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
}
