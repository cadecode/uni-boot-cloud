package top.cadecode.uniboot.framework.security.filter;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import top.cadecode.uniboot.common.core.consts.CacheKeyPrefix;
import top.cadecode.uniboot.common.core.datasource.CacheKeyGenerator;
import top.cadecode.uniboot.common.core.enums.AuthModelEnum;
import top.cadecode.uniboot.common.core.enums.error.AuthErrorEnum;
import top.cadecode.uniboot.common.core.util.RedisUtil;
import top.cadecode.uniboot.framework.bean.dto.SysUserDto;
import top.cadecode.uniboot.framework.bean.dto.SysUserDto.SysUserDetailsDto;
import top.cadecode.uniboot.framework.security.TokenAuthFilter;
import top.cadecode.uniboot.framework.security.TokenAuthHolder;

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

    private final TokenAuthHolder tokenAuthHolder;

    @Override
    public AuthModelEnum getAuthModel() {
        return AuthModelEnum.REDIS;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String uuidToken = tokenAuthHolder.getTokenFromRequest(request);
        // token 不存在
        if (StrUtil.isEmpty(uuidToken)) {
            filterChain.doFilter(request, response);
            return;
        }
        // 查询 redis 中 token
        String loginUserKey = CacheKeyGenerator.key(CacheKeyPrefix.LOGIN_USER, uuidToken);
        SysUserDetailsDto sysUserDetailsDto = RedisUtil.get(loginUserKey, SysUserDto.SysUserDetailsDto.class);
        // redis 中用户不存在
        if (Objects.isNull(sysUserDetailsDto)) {
            writeResponse(response, AuthErrorEnum.TOKEN_EXPIRED, requestURI);
            return;
        }
        // 用户存在，刷新过期时间
        RedisUtil.expire(loginUserKey, tokenAuthHolder.getExpiration(), TimeUnit.SECONDS);
        // 设置 AuthenticationToken
        setAuthentication(request, sysUserDetailsDto);
        filterChain.doFilter(request, response);
    }
}
