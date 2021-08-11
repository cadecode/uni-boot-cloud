package top.cadecode.simple.util;

/**
 * @author Cade Li
 * @date 2021/7/15
 * @description: 字符串工具类
 */
public class StringUtil {

    /**
     * 判断是否为 null 字符串或空字符
     *
     * @return boolean
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
