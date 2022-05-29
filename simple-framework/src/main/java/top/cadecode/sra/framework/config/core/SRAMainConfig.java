package top.cadecode.sra.framework.config.core;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Cade Li
 * @date 2022/5/21
 * @description simple-rest-admin 主配置类，维护一些功能开关和全局变量
 */
@Slf4j
@Data
@RequiredArgsConstructor
@Configuration
@ConfigurationProperties("sra.config")
public class SRAMainConfig {

    /**
     * 版本号
     */
    private String version;

    /**
     * 是否开启动态数据源配置
     */
    private boolean dynamicDsOn;

    /**
     * 是否开启 swagger 文档
     */
    private boolean swaggerOn;
}
