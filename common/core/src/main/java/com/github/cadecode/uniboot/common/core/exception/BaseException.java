package com.github.cadecode.uniboot.common.core.exception;

import cn.hutool.core.util.StrUtil;

/**
 * 基础异常类
 *<p>message 支持字符串模板，由 hutool {@code StrUtil.format} 提供
 *
 * @author Cade Li
 * @since 2024/4/23
 */
public class BaseException extends RuntimeException {
    public BaseException() {
    }

    public BaseException(String message, Object... params) {
        super(StrUtil.format(message, params));
    }

    public BaseException(String message, Throwable cause, Object... params) {
        super(StrUtil.format(message, params), cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... params) {
        super(StrUtil.format(message, params), cause, enableSuppression, writableStackTrace);
    }
}
