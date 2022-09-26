package top.cadecode.uniboot.common.util;

import cn.hutool.core.util.ObjectUtil;
import top.cadecode.uniboot.common.exception.UniErrorCode;
import top.cadecode.uniboot.common.exception.UniException;

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
     * @param expression 布尔表达式
     * @param exception  运行时异常
     */
    public static void isTrue(boolean expression, RuntimeException exception) {
        if (expression) {
            throw exception;
        }
    }

    /**
     * 判断是否为 true，如果是就抛出运行时异常 UniException
     *
     * @param expression 布尔表达式
     * @param moreInfo   异常信息
     */
    public static void isTrue(boolean expression, String moreInfo) {
        isTrue(expression, UniException.of(moreInfo));
    }

    /**
     * 判断是否为 true，如果是就抛出运行时异常 UniException
     *
     * @param expression 布尔表达式
     * @param errorCode  错误码
     * @param moreInfo   异常信息
     */
    public static void isTrue(boolean expression, UniErrorCode errorCode, String moreInfo) {
        isTrue(expression, UniException.of(errorCode, moreInfo));
    }

    /**
     * 判断是否为 true，如果是就抛出运行时异常 UniException，指定 caused
     *
     * @param expression 布尔表达式
     * @param errorCode  错误码
     * @param throwable  caused 异常
     * @param moreInfo   异常信息
     */
    public static void isTrue(boolean expression, UniErrorCode errorCode, Throwable throwable, String moreInfo) {
        isTrue(expression, UniException.of(errorCode, throwable, moreInfo));
    }

    /**
     * 判断是否为 false，如果是就抛出运行时异常
     *
     * @param expression 布尔表达式
     * @param exception  运行时异常
     */
    public static void isFalse(boolean expression, RuntimeException exception) {
        isTrue(!expression, exception);
    }

    /**
     * 判断是否为 false，如果是就抛出运行时异常 UniException
     *
     * @param expression 布尔表达式
     * @param moreInfo   异常信息
     */
    public static void isFalse(boolean expression, String moreInfo) {
        isTrue(!expression, UniException.of(moreInfo));
    }

    /**
     * 判断是否为 false，如果是就抛出运行时异常 UniException
     *
     * @param expression 布尔表达式
     * @param errorCode  错误码
     * @param moreInfo   异常信息
     */
    public static void isFalse(boolean expression, UniErrorCode errorCode, String moreInfo) {
        isTrue(!expression, UniException.of(errorCode, moreInfo));
    }

    /**
     * 判断是否为 false，如果是就抛出运行时异常 UniException，指定 caused
     *
     * @param expression 布尔表达式
     * @param errorCode  错误码
     * @param throwable  caused 异常
     * @param moreInfo   异常信息
     */
    public static void isFalse(boolean expression, UniErrorCode errorCode, Throwable throwable, String moreInfo) {
        isTrue(!expression, UniException.of(errorCode, throwable, moreInfo));
    }

    /**
     * 判断是否为 null，如果是就抛出运行时异常
     *
     * @param o         对象
     * @param exception 运行时异常
     */
    public static void isNull(Object o, RuntimeException exception) {
        isTrue(ObjectUtil.isNull(o), exception);
    }

    /**
     * 判断是否为 null，如果是就抛出运行时异常 UniException
     *
     * @param o        对象
     * @param moreInfo 异常信息
     */
    public static void isNull(Object o, String moreInfo) {
        isTrue(ObjectUtil.isNull(o), UniException.of(moreInfo));
    }

    /**
     * 判断是否为 null，如果是就抛出运行时异常 UniException
     *
     * @param o         对象
     * @param errorCode 错误码
     * @param moreInfo  异常信息
     */
    public static void isNull(Object o, UniErrorCode errorCode, String moreInfo) {
        isTrue(ObjectUtil.isNull(o), UniException.of(errorCode, moreInfo));
    }

    /**
     * 判断是否为 null，如果是就抛出运行时异常 UniException，指定 caused
     *
     * @param o         布尔表达式
     * @param errorCode 对象
     * @param throwable caused 异常
     * @param moreInfo  异常信息
     */
    public static void isNull(Object o, UniErrorCode errorCode, Throwable throwable, String moreInfo) {
        isTrue(ObjectUtil.isNull(o), UniException.of(errorCode, throwable, moreInfo));
    }

    /**
     * 判断是否不为 null，如果是就抛出运行时异常
     *
     * @param o         对象
     * @param exception 运行时异常
     */
    public static void isNotNull(Object o, RuntimeException exception) {
        isTrue(ObjectUtil.isNotNull(o), exception);
    }

    /**
     * 判断是否不为 null，如果是就抛出运行时异常 UniException
     *
     * @param o        对象
     * @param moreInfo 异常信息
     */
    public static void isNotNull(Object o, String moreInfo) {
        isTrue(ObjectUtil.isNotNull(o), UniException.of(moreInfo));
    }

    /**
     * 判断是否不为 null，如果是就抛出运行时异常 UniException
     *
     * @param o         对象
     * @param errorCode 错误码
     * @param moreInfo  异常信息
     */
    public static void isNotNull(Object o, UniErrorCode errorCode, String moreInfo) {
        isTrue(ObjectUtil.isNotNull(o), UniException.of(errorCode, moreInfo));
    }

    /**
     * 判断是否不为 null，如果是就抛出运行时异常 UniException，指定 caused
     *
     * @param o         布尔表达式
     * @param errorCode 对象
     * @param throwable caused 异常
     * @param moreInfo  异常信息
     */
    public static void isNotNull(Object o, UniErrorCode errorCode, Throwable throwable, String moreInfo) {
        isTrue(ObjectUtil.isNotNull(o), UniException.of(errorCode, throwable, moreInfo));
    }

    /**
     * 判断是否为空，如果是就抛出运行时异常
     *
     * @param o         对象
     * @param exception 运行时异常
     */
    public static void isEmpty(Object o, RuntimeException exception) {
        isTrue(ObjectUtil.isEmpty(o), exception);
    }

    /**
     * 判断是否为空，如果是就抛出运行时异常 UniException
     *
     * @param o        对象
     * @param moreInfo 异常信息
     */
    public static void isEmpty(Object o, String moreInfo) {
        isTrue(ObjectUtil.isEmpty(o), UniException.of(moreInfo));
    }

    /**
     * 判断是否为空，如果是就抛出运行时异常 UniException
     *
     * @param o         对象
     * @param errorCode 错误码
     * @param moreInfo  异常信息
     */
    public static void isEmpty(Object o, UniErrorCode errorCode, String moreInfo) {
        isTrue(ObjectUtil.isEmpty(o), UniException.of(errorCode, moreInfo));
    }

    /**
     * 判断是否为空，如果是就抛出运行时异常 UniException，指定 caused
     *
     * @param o         布尔表达式
     * @param errorCode 对象
     * @param throwable caused 异常
     * @param moreInfo  异常信息
     */
    public static void isEmpty(Object o, UniErrorCode errorCode, Throwable throwable, String moreInfo) {
        isTrue(ObjectUtil.isEmpty(o), UniException.of(errorCode, throwable, moreInfo));
    }

    /**
     * 判断是否不为空，如果是就抛出运行时异常
     *
     * @param o         对象
     * @param exception 运行时异常
     */
    public static void isNotEmpty(Object o, RuntimeException exception) {
        isTrue(ObjectUtil.isNotEmpty(o), exception);
    }

    /**
     * 判断是否不为空，如果是就抛出运行时异常 UniException
     *
     * @param o        对象
     * @param moreInfo 异常信息
     */
    public static void isNotEmpty(Object o, String moreInfo) {
        isTrue(ObjectUtil.isNotEmpty(o), UniException.of(moreInfo));
    }

    /**
     * 判断是否不为空，如果是就抛出运行时异常 UniException
     *
     * @param o         对象
     * @param errorCode 错误码
     * @param moreInfo  异常信息
     */
    public static void isNotEmpty(Object o, UniErrorCode errorCode, String moreInfo) {
        isTrue(ObjectUtil.isNotEmpty(o), UniException.of(errorCode, moreInfo));
    }

    /**
     * 判断是否不为空，如果是就抛出运行时异常 UniException，指定 caused
     *
     * @param o         布尔表达式
     * @param errorCode 对象
     * @param throwable caused 异常
     * @param moreInfo  异常信息
     */
    public static void isNotEmpty(Object o, UniErrorCode errorCode, Throwable throwable, String moreInfo) {
        isTrue(ObjectUtil.isNotEmpty(o), UniException.of(errorCode, throwable, moreInfo));
    }

}
