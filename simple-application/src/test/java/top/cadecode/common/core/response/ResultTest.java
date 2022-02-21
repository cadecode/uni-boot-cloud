package top.cadecode.common.core.response;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import top.cadecode.common.util.JsonUtil;

/**
 * @author Cade Li
 * @date 2021/7/16
 * @description
 */
@Slf4j
public class ResultTest {

    @Test
    public void ok() {
        String okJson = JsonUtil.objToStr(Result.ok("hello world!"),
                true);
        log.info("okJson: {}", okJson);
    }

    @Test
    public void of() {
        String failJson = JsonUtil.objToStr(Result.of(ResultCode.UNKNOWN),
                true);
        log.info("failJson: {}", failJson);
    }
}
