package info.cadecode.simple.common.response;

import info.cadecode.simple.SimpleSpringBootApplicationTests;
import info.cadecode.simple.constant.ReasonEnum;
import org.junit.jupiter.api.Test;

/**
 * @author Cade Li
 * @date 2021/7/16
 * @description:
 */
public class SimpleResTest extends SimpleSpringBootApplicationTests {

    @Test
    public void ok() {
        String okJson = SimpleRes.ok("hello world!")
                .json();
        log.info("okJson: {}", okJson);
    }

    @Test
    public void error() {
        String errorJson = SimpleRes.error("hello world!")
                .json();
        log.info("errorJson: {}", errorJson);
    }

    @Test
    public void reason() {
        String reasonJson = SimpleRes.reason(ReasonEnum.BAD_REQ)
                .json();
        log.info("reasonJson: {}", reasonJson);
    } 
}
