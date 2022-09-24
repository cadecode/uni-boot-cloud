package top.cadecode.uniboot.common.annotation;

import java.lang.annotation.*;

/**
 * 指定动态数据源注解
 *
 * @author Cade Li
 * @date 2022/5/10
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DynamicDS {
    String value();
}
