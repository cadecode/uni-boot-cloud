package top.cadecode.common.util;

import com.nimbusds.jwt.JWTClaimsSet;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.cadecode.application.Application;

import java.util.Collections;
import java.util.Date;

/**
 * @author Cade Li
 * @date 2021/12/10
 * @description Token 工具类测试类
 */
@Slf4j
@SpringBootTest(classes = Application.class)
public class TokenUtilTest {

    @Autowired
    TokenUtil tokenUtil;

    @Test
    public void generateToken() {
        String token = tokenUtil.generateToken(1, "user", Collections.singletonList("user"));
        log.info("token: {}", token);
        JWTClaimsSet set = tokenUtil.verifyToken(token);
        log.info("set: {}", set);
        Date expirationTime = set.getExpirationTime();
        log.info("expirationTime: {}", expirationTime);
    }
}
