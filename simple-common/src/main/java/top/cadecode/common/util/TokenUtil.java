package top.cadecode.common.util;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import top.cadecode.common.core.exception.CommonException;
import top.cadecode.common.enums.FrameErrorEnum;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author Cade Li
 * @date 2021/12/10
 * @description Token 工具类
 */
@Data
@Component
@ConfigurationProperties(prefix = "custom.jwt")
public class TokenUtil {

    private String header;
    private String refreshHeader;
    private Long expiration;
    private Long refreshExpiration;
    private String secret;

    private static final String ID_KEY = "id";
    private static final String NAME_KEY = "name";
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
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .claim(ID_KEY, id)
                .claim(NAME_KEY, name)
                .claim(ROLES_KEY, roles)
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
    public JWTClaimsSet verifyToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(secret);
            boolean verify = signedJWT.verify(verifier);
            if (verify) {
                return signedJWT.getJWTClaimsSet();
            }
            return null;
        } catch (Exception e) {
            throw CommonException.of(FrameErrorEnum.JWT_VERIFY_ERROR).suppressed(e);
        }
    }

    /**
     * 从 claimsSet 获取 id
     *
     * @param claimsSet claims
     * @return id
     * @throws ParseException 转换异常
     */
    public long getIdFromClaims(JWTClaimsSet claimsSet) throws ParseException {
        return claimsSet.getLongClaim(ID_KEY);
    }

    /**
     * 从 claimsSet 获取 name
     *
     * @param claimsSet claims
     * @return name
     * @throws ParseException 转换异常
     */
    public String getNameFromClaims(JWTClaimsSet claimsSet) throws ParseException {
        return claimsSet.getStringClaim(NAME_KEY);
    }

    /**
     * 从 claimsSet 获取 roles
     *
     * @param claimsSet claims
     * @return roles
     * @throws ParseException 转换异常
     */
    public List<String> getRolesFromClaims(JWTClaimsSet claimsSet) throws ParseException {
        return claimsSet.getStringListClaim(ROLES_KEY);
    }

    /**
     * 判断时间是否过期
     *
     * @param expiredDate 最大过期时间
     * @return 是否过期
     */
    public boolean isExpired(Date expiredDate) {
        return expiredDate.getTime() < System.currentTimeMillis();
    }

    /**
     * 判断时间是否过期
     *
     * @param startDate  起始时间
     * @param expiration 秒数
     * @return 是否过期
     */
    public boolean isExpired(Date startDate, long expiration) {
        return isExpired(new Date(startDate.getTime() + expiration * 1000));
    }
}
