package top.cadecode.uniboot;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 用于生成加密配置的测试工具
 *
 * @author Cade Li
 * @since 2023/4/24
 */
@SpringBootTest
public class JasyptEncryptorTest {

    @Autowired
    private StringEncryptor stringEncryptor;

    @Test
    void encrypt() {
        // 执行加密前需要在配置文件中配置jasypt.encryptor.password
        stringEncryptor.encrypt("string waiting encrypt");
    }

}
