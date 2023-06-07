package top.cadecode.uniboot.common.core.enums;

/**
 * Token 校验模式枚举
 *
 * @author Cade Li
 * @date 2022/5/28
 */
public enum AuthModelEnum {

    /**
     * JWT 模式，快过期时下发新 token
     */
    JWT,

    /**
     * Redis + Token 模式，访问时刷新过期时间
     */
    REDIS
}
