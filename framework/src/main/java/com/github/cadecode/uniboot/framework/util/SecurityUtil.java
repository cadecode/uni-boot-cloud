package com.github.cadecode.uniboot.framework.util;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import com.github.cadecode.uniboot.framework.bean.dto.SysUserDto.SysUserDetailsDto;
import com.github.cadecode.uniboot.framework.config.SecurityConfig.SecurityProperties;
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

    public static String getHeader() {
        return PROPERTIES.getToken().getHeader();
    }

    public static Long getExpiration() {
        return PROPERTIES.getToken().getExpiration();
    }

    public static String getSecret() {
        return PROPERTIES.getToken().getSecret();
    }

    /**
     * 从request对象解析token，cookie or header
     *
     * @param request HttpServletRequest
     * @return token
     */
    public static String getTokenFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (ObjectUtil.isNotEmpty(cookies)) {
            Optional<Cookie> optionalCookie = Arrays.stream(cookies)
                    .filter(c -> ObjectUtil.equal(c.getName(), getHeader()))
                    .findAny();
            if (optionalCookie.isPresent()) {
                return optionalCookie.get().getValue();
            }
        }
        return request.getHeader(getHeader());
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
        if (ObjectUtil.isNull(authentication)) {
            authentication = SecurityContextHolder.getContext().getAuthentication();
        }
        return authentication != null && authentication.getPrincipal() instanceof SysUserDetailsDto;
    }

    /**
     * SpringSecurity 相关，取出 UserDetails SysUserDetailsDto
     *
     * @param authentication Security认证信息
     * @return SysUserDetailsDto
     */
    public static SysUserDetailsDto getUserDetails(Authentication authentication) {
        if (ObjectUtil.isNull(authentication)) {
            authentication = SecurityContextHolder.getContext().getAuthentication();
        }
        if (authentication != null && authentication.getPrincipal() instanceof SysUserDetailsDto) {
            return (SysUserDetailsDto) authentication.getPrincipal();
        }
        return null;
    }

    /**
     * SpringSecurity 相关，取出用户名
     */
    public static String getUsername() {
        SysUserDetailsDto userDetails = getUserDetails(null);
        return Optional.ofNullable(userDetails)
                .map(SysUserDetailsDto::getUsername)
                .map(java.lang.String::valueOf)
                .orElse(null);
    }

    @Override
    public void afterPropertiesSet() {
        PROPERTIES = properties;
    }
}
