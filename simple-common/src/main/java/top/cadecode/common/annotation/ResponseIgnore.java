package top.cadecode.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Cade Li
 * @date 2021/8/24
 * @description 类或接口上添加此注解，则不按 SimpleRes 统一格式返回响应
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface ResponseIgnore {
    boolean value() default true;
}
