package top.cadecode.uniboot.common.consts;

/**
 * Redis key 命名前缀
 *
 * @author Cade Li
 * @date 2022/5/29
 */
public interface CacheKeyPrefix {

    /**
     * 登录用户信息
     */
    String LOGIN_USER = "login:user";

    /**
     * Api Roles 映射关系
     */
    String API_ROLES = "api:roles";
}
