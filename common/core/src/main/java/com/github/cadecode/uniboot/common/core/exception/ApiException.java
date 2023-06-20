package com.github.cadecode.uniboot.common.core.exception;

import com.github.cadecode.uniboot.common.core.enums.ApiErrorCode;
import lombok.Getter;

import java.util.Objects;

/**
 * 通用顶级异常类
 *
 * @author Cade Li
 * @date 2022/5/8
 */
@Getter
public class ApiException extends RuntimeException {

    /**
     * 错误信息码
     */
    private final ApiErrorCode errorCode;

    /**
     * 更多错误信息
     */
    private final String moreInfo;

    /**
     * 抛出未知异常
     *
     * @param moreInfo 更多异常信息
     * @return ApiException
     */
    public static ApiException of(String moreInfo) {
        return of(ApiErrorCode.UNKNOWN, moreInfo);
    }

    /**
     * 抛出未知异常
     *
     * @param throwable cause
     * @param moreInfo  更多异常信息
     * @return ApiException
     */
    public static ApiException of(Throwable throwable, String moreInfo) {
        return of(ApiErrorCode.UNKNOWN, throwable, moreInfo);
    }

    /**
     * 根据 ApiErrorCode 抛出异常
     *
     * @param errorCode 错误信息码
     * @param moreInfo  更多异常信息
     * @return ApiException
     */
    public static ApiException of(ApiErrorCode errorCode, String moreInfo) {
        return of(errorCode, null, moreInfo);
    }

    /**
     * 根据 ApiErrorCode 抛出异常
     *
     * @param errorCode 错误信息码
     * @param throwable cause
     * @return ApiException
     */
    public static ApiException of(ApiErrorCode errorCode, Throwable throwable) {
        return of(errorCode, throwable, null);
    }

    /**
     * 根据 ApiErrorCode 抛出异常
     *
     * @param errorCode 错误信息码
     * @param throwable cause
     * @param moreInfo  更多异常信息
     * @return ApiException
     */
    public static ApiException of(ApiErrorCode errorCode, Throwable throwable, String moreInfo) {
        return new ApiException(errorCode, throwable, moreInfo);
    }

    /**
     * 私有构造
     *
     * @param errorCode 错误信息码
     * @param throwable cause
     * @param moreInfo  更多异常信息
     */
    private ApiException(ApiErrorCode errorCode, Throwable throwable, String moreInfo) {
        super(generateMessage(errorCode, moreInfo), throwable);
        this.errorCode = errorCode;
        this.moreInfo = moreInfo;
    }

    /**
     * 根据 ApiErrorCode、moreInfo 构造错误信息
     *
     * @param errorCode 错误信息码
     * @param moreInfo  更多异常信息
     * @return 完整异常信息
     */
    private static String generateMessage(ApiErrorCode errorCode, String moreInfo) {
        String message = "";
        // 拼接 [错误码-错误信息]
        if (Objects.nonNull(errorCode)) {
            message += "[" + errorCode.getCode() + "-" + errorCode.getMessage() + "]";
        }
        // 拼接更多异常信息
        if (Objects.nonNull(moreInfo)) {
            message += moreInfo;
        }
        return message;
    }
}
