package com.github.cadecode.uniboot.common.core.web.response;

/**
 * Http 状态码
 *
 * @author Cade Li
 * @date 2022/5/8
 */
public interface ApiStatus {

    /**
     * 200	OK
     */
    int OK = 200;
    /**
     * 400	错误的请求
     */
    int BAD_REQUEST = 400;
    /**
     * 401	未验证
     */
    int NO_AUTHENTICATION = 401;
    /**
     * 403	被拒绝
     */
    int NO_AUTHORITY = 403;
    /**
     * 404	无法找到
     */
    int NOT_FOUND = 404;
    /**
     * 405	请求方法不合适
     */
    int BAD_REQUEST_METHOD = 405;
    /**
     * 410	已下线
     */
    int NOT_SUPPORT = 410;
    /**
     * 429	过多的请求
     */
    int TOO_MANY_REQUESTS = 429;
    /**
     * 500	内部服务错误
     */
    int SERVER_ERROR = 500;
    /**
     * 502	无效代理
     */
    int INVALID_AGENT = 502;
    /**
     * 503	服务暂时失效
     */
    int SERVER_UNAVAILABLE = 503;
    /**
     * 504	代理超时
     */
    int AGENT_TIMEOUT = 504;
}
