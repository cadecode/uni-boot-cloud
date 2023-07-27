package com.github.cadecode.uniboot.framework.api.security.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.github.cadecode.uniboot.common.core.extension.strategy.StrategyContext;
import com.github.cadecode.uniboot.common.core.util.TokenUtil;
import com.github.cadecode.uniboot.framework.api.bean.dto.SysUserDto.SysUserDetailsDto;
import com.github.cadecode.uniboot.framework.api.enums.AuthErrorEnum;
import com.github.cadecode.uniboot.framework.api.enums.AuthModelEnum;
import com.github.cadecode.uniboot.framework.api.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT 模式校验过滤器
 *
 * @author Cade Li
 * @date 2022/5/28
 */
@RequiredArgsConstructor
@Component
public class JwtTokenAuthFilterService extends TokenAuthFilterService {

    @Override
    public void filter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String jwtToken = SecurityUtil.getTokenFromRequest(request);
        // token 不存在
        if (StrUtil.isEmpty(jwtToken)) {
            filterChain.doFilter(request, response);
            return;
        }
        // token 不合法
        if (!TokenUtil.verifyToken(jwtToken, SecurityUtil.getSecret())) {
            writeResponse(response, AuthErrorEnum.TOKEN_ERROR, requestURI);
            return;
        }
        // token 已过期
        if (TokenUtil.isExpired(jwtToken, SecurityUtil.getSecret())) {
            writeResponse(response, AuthErrorEnum.TOKEN_EXPIRED, requestURI);
            return;
        }
        // 获取 jwt 内容
        JSONObject payload = TokenUtil.getPayload(jwtToken);
        SysUserDetailsDto sysUserDetailsDto = payload.toBean(SysUserDetailsDto.class);
        // 设置 AuthenticationToken
        setAuthentication(request, sysUserDetailsDto);
        // 判断是否需要刷新 token
        // 获取过期时间，单位秒
        long expiresAt = Long.parseLong(String.valueOf(payload.get("exp")));
        // 过期时间的一半，秒转为毫秒
        long halfExpiration = SecurityUtil.getExpiration() / 2;
        // 如果当时时间距离过期时间不到配置的 expiration 一半，就下发新的 token
        if (expiresAt - System.currentTimeMillis() / 1000 < halfExpiration) {
            // 生成 jwt token
            String newJwtToken = TokenUtil.generateToken(sysUserDetailsDto.getId(), sysUserDetailsDto.getUsername(), sysUserDetailsDto.getRoles(),
                    SecurityUtil.getExpiration(), SecurityUtil.getSecret());
            // token 放在请求头
            response.addHeader(SecurityUtil.getHeader(), newJwtToken);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public boolean supports(StrategyContext delimiter) {
        return delimiter.getStrategyType() == AuthModelEnum.JWT;
    }
}
