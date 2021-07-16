package info.cadecode.simple;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SimpleSpringBootApplicationTests {

    public Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    void contextLoads() {
    }

}
