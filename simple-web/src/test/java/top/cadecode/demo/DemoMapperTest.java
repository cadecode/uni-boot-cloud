package top.cadecode.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.cadecode.model.demo.DemoMapper;
import top.cadecode.web.WebApplication;

/**
 * @author Cade Li
 * @date 2021/12/3
 * @description ToDo
 */
@SpringBootTest(classes = WebApplication.class)
public class DemoMapperTest {

    @Autowired
    DemoMapper demoMapper;

    @Test
    public void test() {
        System.out.println(demoMapper.getOne());
        System.out.println(demoMapper.getTwo());
    }

}
