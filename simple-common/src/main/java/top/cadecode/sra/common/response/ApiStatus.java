package top.cadecode.sra.common.response;

/**
 * @author Cade Li
 * @date 2022/5/8
 * @description Http 状态码
 */
public class ApiStatus {

    /**
     * 200	OK
     */
    public static final int OK = 200;
    /**
     * 400	错误的请求
     */
    public static final int BAD_REQUEST = 400;
    /**
     * 401	未验证
     */
    public static final int NO_AUTHENTICATION = 401;
    /**
     * 403	被拒绝
     */
    public static final int NO_AUTHORITY = 403;
    /**
     * 404	无法找到
     */
    public static final int NOT_FOUND = 404;
    /**
     * 405	请求方法不合适
     */
    public static final int BAD_REQUEST_METHOD = 405;
    /**
     * 410	已下线
     */
    public static final int NOT_SUPPORT = 410;
    /**
     * 429	过多的请求
     */
    public static final int TOO_MANY_REQUESTS = 429;
    /**
     * 500	内部服务错误
     */
    public static final int SERVER_ERROR = 500;
    /**
     * 502	无效代理
     */
    public static final int INVALID_AGENT = 502;
    /**
     * 503	服务暂时失效
     */
    public static final int SERVER_UNAVAILABLE = 503;
    /**
     * 504	代理超时
     */
    public static final int AGENT_TIMEOUT = 504;
}
