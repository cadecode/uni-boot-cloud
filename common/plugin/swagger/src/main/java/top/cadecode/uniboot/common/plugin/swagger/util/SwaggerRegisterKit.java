package top.cadecode.uniboot.common.plugin.swagger.util;

import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import top.cadecode.uniboot.common.plugin.swagger.config.SwaggerProperties;

/**
 * Swagger Docket 注册工具类
 *
 * @author Cade Li
 * @since 2023/6/8
 */
@Slf4j
@RequiredArgsConstructor
public class SwaggerRegisterKit implements ApplicationContextAware {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (applicationContext instanceof ConfigurableApplicationContext) {
            this.applicationContext = (ConfigurableApplicationContext) applicationContext;
        }
    }

    /**
     * 配置 Swagger Docket
     */
    public void registerModule(SwaggerProperties prop) {
        log.info("Starting to config swagger dockets");
        if (ObjectUtil.isEmpty(prop.getModule()) || ObjectUtil.isEmpty(applicationContext)) {
            log.info("Swagger dockets config not found");
            return;
        }
        // 获取 BeanFactory，动态注册 Docket
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        prop.getModule().forEach((group, packageName) -> {
            Docket docket = new Docket(DocumentationType.SWAGGER_2)
                    // 设置文档信息
                    .apiInfo(apiInfo(prop))
                    .select()
                    // 设置扫描包
                    .apis(RequestHandlerSelectors.basePackage(packageName))
                    .build()
                    // 设置分组名称
                    .groupName(group);
            beanFactory.registerSingleton(group + "Docket", docket);
        });
        log.info("Configuring swagger dockets ok");
    }

    /**
     * 配置文档信息
     */
    private ApiInfo apiInfo(SwaggerProperties prop) {
        return new ApiInfoBuilder()
                // 设置标题
                .title(prop.getTitle())
                // 设置描述
                .description(prop.getDescription())
                // 设置联系方法
                .contact(new Contact(prop.getName(), prop.getUrl(), prop.getEmail()))
                // 设置版本号
                .version(prop.getVersion())
                .build();
    }
}
