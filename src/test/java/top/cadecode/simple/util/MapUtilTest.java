package top.cadecode.simple.util;

import top.cadecode.simple.SimpleSpringBootApplicationTests;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

/**
 * @author Cade Li
 * @date 2021/7/18
 * @description:
 */
public class MapUtilTest extends SimpleSpringBootApplicationTests {

    @Test
    public void add() {
        String json = MapUtil.create(new HashMap<String, Integer>())
                .add("a", 1)
                .add("b", 2)
                .asJson();
        log.info("json: {}", json);
    }
}
