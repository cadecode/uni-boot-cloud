package top.cadecode.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;


import java.util.HashMap;
import java.util.Map;

/**
 * @author Cade Li
 * @date 2021/7/15
 * @description Jackson json 工具类测试类
 */
@Slf4j
public class JsonUtilTest {

    @Test
    public void objToStr() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "map");
        map.put("writer", "Cade");
        map.put("msg", "hello world!");

        String mapStr = JsonUtil.objToStr(map);
        log.info("mapStr: {}", mapStr);

        String mapPrettyStr = JsonUtil.objToStr(map, true);
        log.info("mapPrettyStr: {}", mapPrettyStr);
    }

    @Test
    public void str2Obj() {
        String str = "{\n" +
                "  \"msg\" : \"hello world!\",\n" +
                "  \"name\" : \"map\",\n" +
                "  \"writer\" : \"Cade\"\n" +
                "}";
        Map<String, String> map = JsonUtil.str2Obj(str, new TypeReference<Map<String, String>>() {});
        log.info("map: {}", map);
    }

}
