package top.cadecode.simple.util;

import top.cadecode.simple.SimpleSpringBootApplicationTests;
import org.junit.jupiter.api.Test;

/**
 * @author Cade Li
 * @date 2021/7/16
 * @description: 字符串工具类测试类
 */
public class StringUtilTest extends SimpleSpringBootApplicationTests {

    @Test
    public void hasEmpty() {
        boolean isEmpty = StringUtil.isEmpty("");
        log.info("isEmpty: {}", isEmpty);

        isEmpty = StringUtil.isEmpty("abc");
        log.info("isEmpty: {}", isEmpty);
    }

}
