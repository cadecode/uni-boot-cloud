package top.cadecode.sra.framework.config.core;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;

/**
 * @author Cade Li
 * @date 2021/8/23
 * @description Swagger2 统一配置类
 */
@Slf4j
@Data
@EnableSwagger2
@EnableKnife4j
@Configuration
@ConfigurationProperties("sra.swagger")
@ConditionalOnProperty(name = "sra.config.swagger-on", havingValue = "true")
public class SwaggerConfig implements WebMvcConfigurer, ApplicationContextAware {

    private ConfigurableApplicationContext applicationContext;

    private final SRAMainConfig mainConfig;

    /**
     * 模块和包名 MAP
     */
    private Map<String, String> module;

    /**
     * 文档标题
     */
    private String title;

    /**
     * 文档描述
     */
    private String description;

    /**
     * 联系人
     */
    private String name;

    /**
     * 项目地址
     */
    private String url;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 配置 Swagger Docket
     */
    @PostConstruct
    public void init() {
        log.info("开始配置 Swagger dockets");
        if (Objects.isNull(module) || Objects.isNull(applicationContext)) {
            log.info("没有找到 Swagger dockets 相关配置");
            return;
        }
        // 获取 BeanFactory，动态注册 Docket
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        module.forEach((group, packageName) -> {
            Docket docket = new Docket(DocumentationType.SWAGGER_2)
                    // 设置文档信息
                    .apiInfo(apiInfo())
                    .select()
                    // 设置扫描包
                    .apis(RequestHandlerSelectors.basePackage(packageName))
                    .build()
                    // 设置分组名称
                    .groupName(group);
            beanFactory.registerSingleton(group + "Docket", docket);
        });
    }

    /**
     * 配置文档信息
     */
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 设置标题
                .title(title)
                // 设置描述
                .description(description)
                // 设置联系方法
                .contact(new Contact(name, url, email))
                // 设置版本号
                .version(mainConfig.getVersion())
                .build();
    }

    /**
     * 添加静态资源，映射 Swagger 网页文件
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (applicationContext instanceof ConfigurableApplicationContext) {
            this.applicationContext = (ConfigurableApplicationContext) applicationContext;
        }
    }
}
