package top.cadecode.uniboot.common.util;

import top.cadecode.uniboot.common.exception.UniErrorCode;
import top.cadecode.uniboot.common.exception.UniException;

import java.util.Objects;

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
     * 判断是否为 null，如果是就抛出运行时异常
     *
     * @param o         对象
     * @param exception 运行时异常
     */
    public static void isNull(Object o, RuntimeException exception) {
        isTrue(Objects.isNull(o), exception);
    }

    /**
     * 判断是否为 null，如果是就抛出运行时异常 UniException
     *
     * @param o        对象
     * @param moreInfo 异常信息
     */
    public static void isNull(Object o, String moreInfo) {
        isNull(o, UniException.of(moreInfo));
    }

    /**
     * 判断是否为 null，如果是就抛出运行时异常 UniException
     *
     * @param o         对象
     * @param errorCode 错误码
     * @param moreInfo  异常信息
     */
    public static void isNull(Object o, UniErrorCode errorCode, String moreInfo) {
        isNull(o, UniException.of(errorCode, moreInfo));
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
        isNull(o, UniException.of(errorCode, throwable, moreInfo));
    }
}
