package top.cadecode.sra.common.annotation;

import java.lang.annotation.*;

/**
 * @author Cade Li
 * @date 2022/5/8
 * @description 是否开启 ApiResult 格式化
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiFormat {
    boolean value() default true;
}
