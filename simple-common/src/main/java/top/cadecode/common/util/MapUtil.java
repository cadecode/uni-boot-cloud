package top.cadecode.common.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Cade Li
 * @date 2021/7/18
 * @description 链式调用的 map 工具类
 */
public class MapUtil {

    public static MapBuilder<String, Object> create() {
        return new MapBuilder<String, Object>(new LinkedHashMap<>());
    }

    public static <K, V> MapBuilder<K, V> create(Map<K, V> map) {
        return new MapBuilder<K, V>(map);
    }

    public static class MapBuilder<K, V> {

        private Map<K, V> map;

        /**
         * 私有构造
         */
        private MapBuilder(Map<K, V> map) {
            this.map = map;
        }

        /**
         * 链式添加
         */
        public MapBuilder<K, V> add(K k, V v) {
            map.put(k, v);
            return this;
        }

        /**
         * 链式删除
         */
        public MapBuilder<K, V> del(K k) {
            map.remove(k);
            return this;
        }

        /**
         * 转 map
         */
        public Map<K, V> asMap() {
            return map;
        }

        /**
         * 转 json 字符串
         */
        public String asJson() {
            return JsonUtil.objToStr(map);
        }
    }
}
