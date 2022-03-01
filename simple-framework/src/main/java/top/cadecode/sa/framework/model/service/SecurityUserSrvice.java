package top.cadecode.sa.framework.model.service;

/**
 * @author Cade Li
 * @date 2022/2/21
 * @description spring security user service
 */
public interface SecurityUserSrvice {

    /**
     * 用户 refresh token 缓存前缀
     */
    String USER_REFRESH_TOKEN_PREFIX = "security:user.refresh:";

}
