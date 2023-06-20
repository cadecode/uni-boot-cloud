package com.github.cadecode.uniboot.common.core.annotation;

import java.lang.annotation.*;

/**
 * Excel 字段注解
 * 基于 hutool-poi，提供注解支持
 *
 * @author Cade Li
 * @date 2023/6/9
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelField {

    /**
     * 按表头对应名称映射字段
     * 读：用于根据表头确定列，比 colIndex 优先级高
     * 写：用于确定写出时的表头
     * 注意：由于使用 BeanUtil 进行 Map 到 Bean 的拷贝，即便 headAlias/colIndex 都没有生效，默认同名属性也会拷贝成功
     */
    String headAlias() default "";

    /**
     * 按列顺序映射字段
     * 读：在没有设置 headAlias 时 / 在读取没有表头的表格时用于确定列
     * 写：用于确定写出时列的顺序
     * 注意：多个字段 colIndex 不相同时只会有一个字段生效
     */
    int colIndex() default -1;

    /**
     * 是否读时忽略
     */
    boolean readIgnore() default false;

    /**
     * 是否写时忽略
     */
    boolean writeIgnore() default false;
}
