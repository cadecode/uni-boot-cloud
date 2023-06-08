package top.cadecode.uniboot.common.core.util;

import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;

import java.util.Date;
import java.util.List;

/**
 * JWT token 工具栏
 *
 * @author Cade Li
 * @date 2023/6/8
 */
public class TokenUtil {

    private static final String ID_KEY = "id";
    private static final String NAME_KEY = "username";
    private static final String ROLES_KEY = "roles";

    /**
     * 生成 Jwt token
     *
     * @param id    用户 ID
     * @param roles 角色
     * @return token 字符串
     */
    public static String generateToken(long id, String name, List<String> roles, Long expiration, String secret) {
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
    public static boolean verifyToken(String token, String secret) {
        try {
            return JWT.of(token)
                    .setKey(secret.getBytes())
                    .verify();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断 token 是否过期
     *
     * @param token token 字符串
     * @return 是否过期
     */
    public static boolean isExpired(String token, String secret) {
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
    public static JSONObject getPayload(String token) {
        return JWT.of(token).getPayloads();
    }

    /**
     * 获取 token 内容 json 串
     *
     * @param token token 字符串
     * @return json 串
     */
    public static String getPayloadStr(String token) {
        return JWT.of(token).getPayloads().toString();
    }
}
