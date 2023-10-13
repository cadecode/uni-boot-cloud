package com.github.cadecode.uniboot.framework.base.util;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjUtil;
import com.github.cadecode.uniboot.framework.api.consts.HttpConst;
import com.github.cadecode.uniboot.framework.base.config.SecurityConfig.SecurityProperties;
import com.github.cadecode.uniboot.framework.base.security.model.SysUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

/**
 * Security token 工具
 *
 * @author Cade Li
 * @date 2022/2/26
 */
@RequiredArgsConstructor
@Component
public class SecurityUtil implements InitializingBean {

    private static SecurityProperties PROPERTIES;

    private final SecurityProperties properties;

    // 从 SecurityConfig 获取 token 配置

    public static Long getExpiration() {
        return PROPERTIES.getTokenConfig().getExpiration();
    }

    public static String getSecret() {
        return PROPERTIES.getTokenConfig().getSecret();
    }

    /**
     * 从request对象解析token，cookie or header
     *
     * @param request HttpServletRequest
     * @return token
     */
    public static String getTokenFromRequest(HttpServletRequest request) {
        // 从请求头获取
        String token = request.getHeader(HttpConst.HEAD_TOKEN);
        if (ObjUtil.isNotEmpty(token)) {
            return token;
        }
        // 从 cookie 中解析
        Cookie[] cookies = request.getCookies();
        if (ObjUtil.isNotEmpty(cookies)) {
            Optional<Cookie> optionalCookie = Arrays.stream(cookies)
                    .filter(c -> ObjUtil.equal(c.getName(), HttpConst.HEAD_TOKEN))
                    .findAny();
            if (optionalCookie.isPresent()) {
                return optionalCookie.get().getValue();
            }
        }
        // 从 url 参数中提取
        return request.getParameter(HttpConst.HEAD_TOKEN);
    }

    /**
     * 生成 UUID token，用作 redis key
     *
     * @return UUID 字符串
     */
    public static String generateUUID() {
        // 使用两个 UUID 拼接
        return UUID.fastUUID().toString(true) + UUID.fastUUID().toString(true);
    }

    /**
     * SpringSecurity 相关，判断是否认证
     *
     * @param authentication Security认证信息
     * @return token
     */
    public static boolean isAuthenticated(Authentication authentication) {
        if (ObjUtil.isNull(authentication)) {
            authentication = SecurityContextHolder.getContext().getAuthentication();
        }
        return authentication != null && authentication.getPrincipal() instanceof SysUserDetails;
    }

    /**
     * SpringSecurity 相关，取出 UserDetails SysUserDetails
     *
     * @param authentication Security认证信息
     * @return SysUserDetails
     */
    public static SysUserDetails getUserDetails(Authentication authentication) {
        if (ObjUtil.isNull(authentication)) {
            authentication = SecurityContextHolder.getContext().getAuthentication();
        }
        if (authentication != null && authentication.getPrincipal() instanceof SysUserDetails) {
            return (SysUserDetails) authentication.getPrincipal();
        }
        return null;
    }

    /**
     * SpringSecurity 相关，取出用户名
     */
    public static String getUsername() {
        SysUserDetails userDetails = getUserDetails(null);
        return Optional.ofNullable(userDetails)
                .map(SysUserDetails::getUsername)
                .map(java.lang.String::valueOf)
                .orElse(null);
    }

    @Override
    public void afterPropertiesSet() {
        PROPERTIES = properties;
    }
}
