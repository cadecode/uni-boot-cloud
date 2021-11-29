package top.cadecode.common.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;

/**
 * @author Cade Li
 * @date 2021/7/18
 * @description
 */
@Slf4j
public class MapUtilTest {

    @Test
    public void add() {
        String json = MapUtil.create(new HashMap<String, Integer>())
                .add("a", 1)
                .add("b", 2)
                .asJson();
        log.info("json: {}", json);
    }
}
