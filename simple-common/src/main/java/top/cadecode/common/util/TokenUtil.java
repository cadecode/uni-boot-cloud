package top.cadecode.common.util;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import top.cadecode.common.core.exception.CommonException;
import top.cadecode.common.enums.FrameErrorEnum;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Cade Li
 * @date 2021/12/10
 * @description Token 工具类
 */
@Data
@Component
@ConfigurationProperties(prefix = "simple.jwt")
public class TokenUtil {

    private String header;
    private String refreshHeader;
    private Long expiration;
    private Long refreshExpiration;
    private String secret;

    /**
     * 生成 token
     *
     * @param id    用户 ID
     * @param roles 角色
     * @return token 字符串
     */
    public String generateToken(long id, List<String> roles) {
        long expiredTime = System.currentTimeMillis() + expiration * 1000;
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .claim("id", id).
                claim("roles", roles)
                .expirationTime(new Date(expiredTime)).build();
        SignedJWT signedJWT = new SignedJWT(jwsHeader, jwtClaimsSet);
        try {
            signedJWT.sign(new MACSigner(secret));
            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw CommonException.of(FrameErrorEnum.JWT_CREATE_ERROR).suppressed(e);
        }
    }

    /**
     * 校验 token
     *
     * @param token token 字符串
     * @return 是否通过校验
     */
    public Map<String, Object> verifyToken(String token) {
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            JWSVerifier verifier = new MACVerifier(secret);
            boolean verify = jwsObject.verify(verifier);
            if (verify) {
                return jwsObject.getPayload().toJSONObject();
            }
            return null;
        } catch (Exception e) {
            throw CommonException.of(FrameErrorEnum.JWT_VERIFY_ERROR).suppressed(e);
        }
    }
}
