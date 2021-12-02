package top.cadecode.common.response;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import top.cadecode.common.constant.ResCode;
import top.cadecode.common.util.JsonUtil;

/**
 * @author Cade Li
 * @date 2021/7/16
 * @description
 */
@Slf4j
public class SimpleResTest {

    @Test
    public void ok() {
        String okJson = JsonUtil.objToStr(SimpleRes.ok("hello world!"),
                true);
        log.info("okJson: {}", okJson);
    }

    @Test
    public void of() {
        String failJson = JsonUtil.objToStr(SimpleRes.of(ResCode.UNKNOWN),
                true);
        log.info("failJson: {}", failJson);
    }
}
