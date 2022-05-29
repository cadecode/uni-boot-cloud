package top.cadecode.sra.common.datasource;

/**
 * @author Cade Li
 * @date 2022/5/29
 * @description Redis key 命名生成器
 */
public class CacheKeyGenerator {

    /**
     * 生成 ： 分割的 redis key
     *
     * @param prefix 前缀
     * @param extra  其他字符串
     * @return redis key
     */
    public static String key(String prefix, String... extra) {
        StringBuilder prefixBuilder = new StringBuilder(prefix);
        for (String str : extra) {
            prefixBuilder.append(":").append(str);
        }
        return prefixBuilder.toString();
    }

}
