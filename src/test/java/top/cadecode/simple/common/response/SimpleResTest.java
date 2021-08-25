package top.cadecode.simple.common.response;

import org.junit.jupiter.api.Test;
import top.cadecode.simple.SimpleSpringBootApplicationTests;
import top.cadecode.simple.constant.ErrorEnum;
import top.cadecode.simple.util.JsonUtil;

/**
 * @author Cade Li
 * @date 2021/7/16
 * @description:
 */
public class SimpleResTest extends SimpleSpringBootApplicationTests {

    @Test
    public void ok() {
        String okJson = JsonUtil.objToStr(SimpleRes.ok("hello world!"),
                true);
        log.info("okJson: {}", okJson);
    }

    @Test
    public void fail() {
        String failJson = JsonUtil.objToStr(SimpleRes.fail(ErrorEnum.UNKNOWN),
                true);
        log.info("failJson: {}", failJson);
    }
}
