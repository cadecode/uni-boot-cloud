package top.cadecode.simple.common.datasource;

import java.lang.annotation.*;

/**
 * @author Cade Li
 * @date 2021/7/21
 * @description: 用于切换数据源的注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String value();
}
