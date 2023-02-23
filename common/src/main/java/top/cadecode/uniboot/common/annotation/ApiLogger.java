package top.cadecode.uniboot.common.annotation;

import java.lang.annotation.*;

/**
 * 用于开启打印接口日志的注解
 *
 * @author Cade Li
 * @date 2021/12/4
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiLogger {
    boolean value() default true;
}
