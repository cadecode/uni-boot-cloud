package top.cadecode.common.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;


/**
 * @author Cade Li
 * @date 2021/7/16
 * @description 字符串工具类测试类
 */
@Slf4j
public class StringUtilTest {

    @Test
    public void isEmpty() {
        boolean isEmpty = StringUtil.isEmpty("");
        log.info("isEmpty: {}", isEmpty);

        isEmpty = StringUtil.isEmpty("abc");
        log.info("isEmpty: {}", isEmpty);
    }

}
