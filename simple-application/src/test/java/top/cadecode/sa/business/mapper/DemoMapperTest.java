package top.cadecode.sa.business.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.cadecode.sa.application.Application;

/**
 * @author Cade Li
 * @date 2021/12/3
 * @description DemoMapper 测试类
 */
@Slf4j
@SpringBootTest(classes = Application.class)
public class DemoMapperTest {

    @Autowired
    DemoMapper demoMapper;

    @Test
    public void test() {
        log.info("第一个数据源：{}", demoMapper.getOne());
        log.info("第二个数据源：{}", demoMapper.getTwo());
    }

}
