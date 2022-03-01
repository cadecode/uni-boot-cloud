package top.cadecode.sa.common.annotation;

import java.lang.annotation.*;

/**
 * @author Cade Li
 * @date 2021/12/4
 * @description 用于开启打印接口日志的注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResLogger {
    boolean value() default true;
}
