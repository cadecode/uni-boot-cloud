package com.github.cadecode.uniboot.framework.base.security.filter;

import cn.hutool.core.util.StrUtil;
import com.github.cadecode.uniboot.common.core.extension.strategy.StrategyContext;
import com.github.cadecode.uniboot.common.plugin.cache.util.KeyGeneUtil;
import com.github.cadecode.uniboot.common.plugin.cache.util.RedisUtil;
import com.github.cadecode.uniboot.framework.api.consts.KeyPrefixConst;
import com.github.cadecode.uniboot.framework.api.enums.AuthErrorEnum;
import com.github.cadecode.uniboot.framework.api.enums.AuthModelEnum;
import com.github.cadecode.uniboot.framework.base.security.model.SysUserDetails;
import com.github.cadecode.uniboot.framework.base.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
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
public class RedisTokenAuthFilterService extends TokenAuthFilterService {

    @Override
    public void handler(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String uuidToken = SecurityUtil.getTokenFromRequest(request);
        // token 不存在
        if (StrUtil.isEmpty(uuidToken)) {
            filterChain.doFilter(request, response);
            return;
        }
        // 查询 redis 中 token
        String loginUserKey = KeyGeneUtil.key(KeyPrefixConst.LOGIN_USER, uuidToken);
        SysUserDetails sysUserDetails = RedisUtil.get(loginUserKey, SysUserDetails.class);
        // redis 中用户不存在
        if (Objects.isNull(sysUserDetails)) {
            writeResponse(response, AuthErrorEnum.TOKEN_EXPIRED, requestURI);
            return;
        }
        // 用户存在，刷新过期时间
        RedisUtil.expire(loginUserKey, SecurityUtil.getExpiration(), TimeUnit.SECONDS);
        // 设置 AuthenticationToken
        setAuthentication(request, sysUserDetails);
        filterChain.doFilter(request, response);
    }

    @Override
    public boolean supports(StrategyContext delimiter) {
        return delimiter.getStrategyType() == AuthModelEnum.REDIS;
    }
}
