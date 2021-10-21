package top.cadecode.simple.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
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

/**
 * @author Li Jun
 * @date 2021/8/23
 * @description Swagger2 配置类
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig implements WebMvcConfigurer {

    // 配置 Swagger Docket
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 设置文档信息
                .apiInfo(apiInfo())
                .select()
                // 设置监听包
                .apis(RequestHandlerSelectors.basePackage("top.cadecode.simple.controller"))
                .build()
                // 设置分组名称
                .groupName("默认分组");
    }

    // 配置文档信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 设置标题
                .title("API Online Document")
                // 设置描述
                .description("simple-spring-boot 在线文档")
                // 设置联系方法
                .contact(new Contact("Cade Li",
                        "https://github.com/cadecode/simple-spring-boot",
                        "cadecode@foxmail.com"))
                // 设置版本号
                .version("0.0.1")
                .build();
    }

    // 添加静态资源，映射 Swagger 网页文件
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
