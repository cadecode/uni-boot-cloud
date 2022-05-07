package top.cadecode.sra.framework.security;

import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author Cade Li
 * @date 2022/2/26
 * @description JWT Token 工具类（基于 hutool）
 */
@Data
@Component
@ConfigurationProperties(prefix = "custom.jwt")
public class JwtTokenHolder {

    /**
     * token 请求头字段
     */
    private String header;
    /**
     * refresh token 请求头字段
     */
    private String refreshHeader;
    /**
     * token 过期时间
     */
    private Long expiration;
    /**
     * refresh token 过期时间
     */
    private Long refreshExpiration;
    /**
     * 密钥
     */
    private String secret;

    private static final String ID_KEY = "id";
    private static final String NAME_KEY = "username";
    private static final String ROLES_KEY = "roles";

    /**
     * 生成 token
     *
     * @param id    用户 ID
     * @param roles 角色
     * @return token 字符串
     */
    public String generateToken(long id, String name, List<String> roles) {
        long expiredTime = System.currentTimeMillis() + expiration * 1000;
        return JWT.create()
                .setPayload(ID_KEY, id)
                .setPayload(NAME_KEY, name)
                .setPayload(ROLES_KEY, roles)
                .setExpiresAt(new Date(expiredTime))
                .setKey(secret.getBytes())
                .sign();
    }

    /**
     * 校验 token 正确性
     *
     * @param token token 字符串
     * @return 是否通过校验
     */
    public boolean verifyToken(String token) {
        return JWT.of(token)
                .setKey(secret.getBytes())
                .verify();
    }

    /**
     * 判断 token 是否过期
     *
     * @param token token 字符串
     * @return 是否过期
     */
    public boolean isExpired(String token) {
        return !JWT.of(token)
                .setKey(secret.getBytes())
                .validate(0);
    }

    /**
     * 获取 token 内容
     *
     * @param token token 字符串
     * @return JSONObject
     */
    public JSONObject getPayload(String token) {
        return JWT.of(token).getPayloads();
    }

}
