package com.github.cadecode.uniboot.framework.api.consts;

/**
 * Security 常量
 *
 * @author Cade Li
 * @since 2023/7/29
 */
public interface SecurityConst {

    /**
     * token 请求头名称
     */
    String HEAD_TOKEN = "token";

    /**
     * 请求来源请求头名称
     */
    String HEAD_SOURCE = "from-source";

    /**
     * 请求来源请求头固定值
     */
    String HEAD_SOURCE_VALUE = "inner-rpc";

    /**
     * 用户信息请求头名称
     */
    String HEAD_USER_DETAILS = "inner-user-details";

    /**
     * X-Forwarded-For
     */
    String HEAD_FORWARDED_FOR = "X-Forwarded-For";

    /**
     * trace id
     */
    String HEAD_TRACE_ID = "trace-id";

    /**
     * User-Agent
     */
    String HEAD_USER_AGENT = "User-Agent";

}
