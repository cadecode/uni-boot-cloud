package com.github.cadecode.uniboot.framework.security.filter;

import cn.hutool.core.util.StrUtil;
import com.github.cadecode.uniboot.common.plugin.cache.util.KeyGeneUtil;
import com.github.cadecode.uniboot.common.plugin.cache.util.RedisUtil;
import com.github.cadecode.uniboot.framework.bean.dto.SysUserDto;
import com.github.cadecode.uniboot.framework.bean.dto.SysUserDto.SysUserDetailsDto;
import com.github.cadecode.uniboot.framework.consts.KeyPrefix;
import com.github.cadecode.uniboot.framework.enums.AuthErrorEnum;
import com.github.cadecode.uniboot.framework.enums.AuthModelEnum;
import com.github.cadecode.uniboot.framework.security.TokenAuthFilter;
import com.github.cadecode.uniboot.framework.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Redis + Token 模式校验过滤器
 *
 * @author Cade Li
 * @date 2022/5/28
 */
@RequiredArgsConstructor
@Component
@ConditionalOnProperty(name = "uni-boot.security.auth-model", havingValue = "redis")
public class RedisTokenAuthFilter extends TokenAuthFilter {

    @Override
    public AuthModelEnum getAuthModel() {
        return AuthModelEnum.REDIS;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String uuidToken = SecurityUtil.getTokenFromRequest(request);
        // token 不存在
        if (StrUtil.isEmpty(uuidToken)) {
            filterChain.doFilter(request, response);
            return;
        }
        // 查询 redis 中 token
        String loginUserKey = KeyGeneUtil.key(KeyPrefix.LOGIN_USER, uuidToken);
        SysUserDetailsDto sysUserDetailsDto = RedisUtil.get(loginUserKey, SysUserDto.SysUserDetailsDto.class);
        // redis 中用户不存在
        if (Objects.isNull(sysUserDetailsDto)) {
            writeResponse(response, AuthErrorEnum.TOKEN_EXPIRED, requestURI);
            return;
        }
        // 用户存在，刷新过期时间
        RedisUtil.expire(loginUserKey, SecurityUtil.getExpiration(), TimeUnit.SECONDS);
        // 设置 AuthenticationToken
        setAuthentication(request, sysUserDetailsDto);
        filterChain.doFilter(request, response);
    }
}
