package com.github.cadecode.uniboot.common.plugin.swagger.config;

import com.github.cadecode.uniboot.common.plugin.swagger.util.SwaggerRegisterKit;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * Swagger WebMvc 配置
 *
 * @author Cade Li
 * @since 2023/6/8
 */
@RequiredArgsConstructor
@EnableSwagger2WebMvc
@EnableKnife4j
@Configuration
@Import(SwaggerRegisterKit.class)
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnProperty(name = "uni-boot.config.swagger-on", havingValue = "true")
public class SwaggerAutoConfig implements WebMvcConfigurer, InitializingBean {

    private final SwaggerProperties swaggerProperties;
    private final SwaggerRegisterKit swaggerRegisterKit;

    /**
     * 添加静态资源，映射 Swagger 网页文件
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void afterPropertiesSet() {
        swaggerRegisterKit.registerModule(swaggerProperties);
    }
}
