package top.cadecode.sra.framework.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.cadecode.sra.framework.convert.EnumConvertorFactory;

/**
 * @author Cade Li
 * @date 2022/6/14
 * @description Spring MVC 配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 添加枚举类的转换器工厂
        registry.addConverterFactory(new EnumConvertorFactory());
    }
}
