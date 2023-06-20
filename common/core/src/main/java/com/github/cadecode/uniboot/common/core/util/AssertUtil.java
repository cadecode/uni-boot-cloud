package com.github.cadecode.uniboot.common.core.util;

import cn.hutool.core.util.ObjectUtil;
import com.github.cadecode.uniboot.common.core.enums.ApiErrorCode;
import com.github.cadecode.uniboot.common.core.exception.ApiException;

import java.util.function.Supplier;

/**
 * 断言工具类
 *
 * @author Cade Li
 * @date 2022/9/24
 */
public class AssertUtil {

    /**
     * 判断是否为 true，如果是就抛出运行时异常
     *
     * @param expression        布尔表达式
     * @param exceptionSupplier 运行时异常提供者
     */
    public static void isTrue(boolean expression, Supplier<RuntimeException> exceptionSupplier) {
        if (expression) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 判断是否为 true，如果是就抛出运行时异常 ApiException
     *
     * @param expression 布尔表达式
     * @param moreInfo   异常信息
     */
    public static void isTrue(boolean expression, String moreInfo) {
        isTrue(expression, () -> ApiException.of(moreInfo));
    }

    /**
     * 判断是否为 true，如果是就抛出运行时异常 ApiException
     *
     * @param expression 布尔表达式
     * @param errorCode  错误码
     * @param moreInfo   异常信息
     */
    public static void isTrue(boolean expression, ApiErrorCode errorCode, String moreInfo) {
        isTrue(expression, () -> ApiException.of(errorCode, moreInfo));
    }

    /**
     * 判断是否为 true，如果是就抛出运行时异常 ApiException，指定 caused
     *
     * @param expression 布尔表达式
     * @param errorCode  错误码
     * @param throwable  caused 异常
     * @param moreInfo   异常信息
     */
    public static void isTrue(boolean expression, ApiErrorCode errorCode, Throwable throwable, String moreInfo) {
        isTrue(expression, () -> ApiException.of(errorCode, throwable, moreInfo));
    }

    /**
     * 判断是否为 false，如果是就抛出运行时异常
     *
     * @param expression        布尔表达式
     * @param exceptionSupplier 运行时异常提供者
     */
    public static void isFalse(boolean expression, Supplier<RuntimeException> exceptionSupplier) {
        isTrue(!expression, exceptionSupplier);
    }

    /**
     * 判断是否为 false，如果是就抛出运行时异常 ApiException
     *
     * @param expression 布尔表达式
     * @param moreInfo   异常信息
     */
    public static void isFalse(boolean expression, String moreInfo) {
        isTrue(!expression, () -> ApiException.of(moreInfo));
    }

    /**
     * 判断是否为 false，如果是就抛出运行时异常 ApiException
     *
     * @param expression 布尔表达式
     * @param errorCode  错误码
     * @param moreInfo   异常信息
     */
    public static void isFalse(boolean expression, ApiErrorCode errorCode, String moreInfo) {
        isTrue(!expression, () -> ApiException.of(errorCode, moreInfo));
    }

    /**
     * 判断是否为 false，如果是就抛出运行时异常 ApiException，指定 caused
     *
     * @param expression 布尔表达式
     * @param errorCode  错误码
     * @param throwable  caused 异常
     * @param moreInfo   异常信息
     */
    public static void isFalse(boolean expression, ApiErrorCode errorCode, Throwable throwable, String moreInfo) {
        isTrue(!expression, () -> ApiException.of(errorCode, throwable, moreInfo));
    }

    /**
     * 判断是否为 null，如果是就抛出运行时异常
     *
     * @param o                 对象
     * @param exceptionSupplier 运行时异常提供者
     */
    public static void isNull(Object o, Supplier<RuntimeException> exceptionSupplier) {
        isTrue(ObjectUtil.isNull(o), exceptionSupplier);
    }

    /**
     * 判断是否为 null，如果是就抛出运行时异常 ApiException
     *
     * @param o        对象
     * @param moreInfo 异常信息
     */
    public static void isNull(Object o, String moreInfo) {
        isTrue(ObjectUtil.isNull(o), () -> ApiException.of(moreInfo));
    }

    /**
     * 判断是否为 null，如果是就抛出运行时异常 ApiException
     *
     * @param o         对象
     * @param errorCode 错误码
     * @param moreInfo  异常信息
     */
    public static void isNull(Object o, ApiErrorCode errorCode, String moreInfo) {
        isTrue(ObjectUtil.isNull(o), () -> ApiException.of(errorCode, moreInfo));
    }

    /**
     * 判断是否为 null，如果是就抛出运行时异常 ApiException，指定 caused
     *
     * @param o         布尔表达式
     * @param errorCode 对象
     * @param throwable caused 异常
     * @param moreInfo  异常信息
     */
    public static void isNull(Object o, ApiErrorCode errorCode, Throwable throwable, String moreInfo) {
        isTrue(ObjectUtil.isNull(o), () -> ApiException.of(errorCode, throwable, moreInfo));
    }

    /**
     * 判断是否不为 null，如果是就抛出运行时异常
     *
     * @param o                 对象
     * @param exceptionSupplier 运行时异常提供者
     */
    public static void isNotNull(Object o, Supplier<RuntimeException> exceptionSupplier) {
        isTrue(ObjectUtil.isNotNull(o), exceptionSupplier);
    }

    /**
     * 判断是否不为 null，如果是就抛出运行时异常 ApiException
     *
     * @param o        对象
     * @param moreInfo 异常信息
     */
    public static void isNotNull(Object o, String moreInfo) {
        isTrue(ObjectUtil.isNotNull(o), () -> ApiException.of(moreInfo));
    }

    /**
     * 判断是否不为 null，如果是就抛出运行时异常 ApiException
     *
     * @param o         对象
     * @param errorCode 错误码
     * @param moreInfo  异常信息
     */
    public static void isNotNull(Object o, ApiErrorCode errorCode, String moreInfo) {
        isTrue(ObjectUtil.isNotNull(o), () -> ApiException.of(errorCode, moreInfo));
    }

    /**
     * 判断是否不为 null，如果是就抛出运行时异常 ApiException，指定 caused
     *
     * @param o         布尔表达式
     * @param errorCode 对象
     * @param throwable caused 异常
     * @param moreInfo  异常信息
     */
    public static void isNotNull(Object o, ApiErrorCode errorCode, Throwable throwable, String moreInfo) {
        isTrue(ObjectUtil.isNotNull(o), () -> ApiException.of(errorCode, throwable, moreInfo));
    }

    /**
     * 判断是否为空，如果是就抛出运行时异常
     *
     * @param o                 对象
     * @param exceptionSupplier 运行时异常提供者
     */
    public static void isEmpty(Object o, Supplier<RuntimeException> exceptionSupplier) {
        isTrue(ObjectUtil.isEmpty(o), exceptionSupplier);
    }

    /**
     * 判断是否为空，如果是就抛出运行时异常 ApiException
     *
     * @param o        对象
     * @param moreInfo 异常信息
     */
    public static void isEmpty(Object o, String moreInfo) {
        isTrue(ObjectUtil.isEmpty(o), () -> ApiException.of(moreInfo));
    }

    /**
     * 判断是否为空，如果是就抛出运行时异常 ApiException
     *
     * @param o         对象
     * @param errorCode 错误码
     * @param moreInfo  异常信息
     */
    public static void isEmpty(Object o, ApiErrorCode errorCode, String moreInfo) {
        isTrue(ObjectUtil.isEmpty(o), () -> ApiException.of(errorCode, moreInfo));
    }

    /**
     * 判断是否为空，如果是就抛出运行时异常 ApiException，指定 caused
     *
     * @param o         布尔表达式
     * @param errorCode 对象
     * @param throwable caused 异常
     * @param moreInfo  异常信息
     */
    public static void isEmpty(Object o, ApiErrorCode errorCode, Throwable throwable, String moreInfo) {
        isTrue(ObjectUtil.isEmpty(o), () -> ApiException.of(errorCode, throwable, moreInfo));
    }

    /**
     * 判断是否不为空，如果是就抛出运行时异常
     *
     * @param o                 对象
     * @param exceptionSupplier 运行时异常提供者
     */
    public static void isNotEmpty(Object o, Supplier<RuntimeException> exceptionSupplier) {
        isTrue(ObjectUtil.isNotEmpty(o), exceptionSupplier);
    }

    /**
     * 判断是否不为空，如果是就抛出运行时异常 ApiException
     *
     * @param o        对象
     * @param moreInfo 异常信息
     */
    public static void isNotEmpty(Object o, String moreInfo) {
        isTrue(ObjectUtil.isNotEmpty(o), () -> ApiException.of(moreInfo));
    }

    /**
     * 判断是否不为空，如果是就抛出运行时异常 ApiException
     *
     * @param o         对象
     * @param errorCode 错误码
     * @param moreInfo  异常信息
     */
    public static void isNotEmpty(Object o, ApiErrorCode errorCode, String moreInfo) {
        isTrue(ObjectUtil.isNotEmpty(o), () -> ApiException.of(errorCode, moreInfo));
    }

    /**
     * 判断是否不为空，如果是就抛出运行时异常 ApiException，指定 caused
     *
     * @param o         布尔表达式
     * @param errorCode 对象
     * @param throwable caused 异常
     * @param moreInfo  异常信息
     */
    public static void isNotEmpty(Object o, ApiErrorCode errorCode, Throwable throwable, String moreInfo) {
        isTrue(ObjectUtil.isNotEmpty(o), () -> ApiException.of(errorCode, throwable, moreInfo));
    }

}
