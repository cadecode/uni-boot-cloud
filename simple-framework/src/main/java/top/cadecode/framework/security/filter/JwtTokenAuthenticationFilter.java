package top.cadecode.framework.security.filter;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import top.cadecode.common.core.response.Result;
import top.cadecode.common.enums.BaseErrorEnum;
import top.cadecode.framework.model.entity.SecurityUser;
import top.cadecode.framework.model.service.SecurityUserSrvice;
import top.cadecode.framework.model.vo.SecurityUserVo;
import top.cadecode.framework.security.JwtTokenHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Cade Li
 * @date 2021/12/12
 * @description JWT Token 校验过滤器
 */
@Component
@RequiredArgsConstructor
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenHolder jwtTokenHolder;
    private final StringRedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String token = request.getHeader(jwtTokenHolder.getHeader());
        // if: token 为空
        if (!StringUtils.hasLength(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        // if: token 不合法
        if (!jwtTokenHolder.verifyToken(token)) {
            writeResponse(response, BaseErrorEnum.TOKEN_ERROR, requestURI);
            return;
        }
        // 从 token 解析出用户信息
        SecurityUserVo securityUserVo = jwtTokenHolder.getPayload(token).toBean(SecurityUserVo.class);
        SecurityUser securityUser = new SecurityUser();
        BeanUtils.copyProperties(securityUserVo, securityUser);
        securityUser.setAuthorities(securityUserVo.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()));
        // if: token 未过期
        if (!jwtTokenHolder.isExpired(token)) {
            setAuthentication(request, securityUser);
            filterChain.doFilter(request, response);
            return;
        }
        String refreshToken = request.getHeader(jwtTokenHolder.getRefreshHeader());
        // if: 请求头中 refresh token 为空
        if (!StringUtils.hasLength(refreshToken)) {
            writeResponse(response, BaseErrorEnum.TOKEN_EXPIRED, requestURI);
            return;
        }
        // refresh token redis key
        String key = SecurityUserSrvice.USER_REFRESH_TOKEN_PREFIX + securityUser.getId();
        String refreshTokenInRedis = redisTemplate.opsForValue().get(key);
        // if: redis 中 refresh token 为空
        if (!StringUtils.hasLength(refreshTokenInRedis)) {
            writeResponse(response, BaseErrorEnum.TOKEN_REFRESH_EXPIRED, requestURI);
            return;
        }
        // if: refresh token 和 redis 中不一致，注意 JSON 格式写入
        if (!refreshToken.equals(refreshTokenInRedis)) {
            writeResponse(response, BaseErrorEnum.TOKEN_REFRESH_ERROR, requestURI);
            return;
        }
        // 重颁发 token
        String newRefreshToken = jwtTokenHolder.generateToken(securityUserVo.getId(),
                securityUserVo.getUsername(), securityUserVo.getRoles());
        response.addHeader(jwtTokenHolder.getRefreshHeader(), newRefreshToken);
        redisTemplate.expire(key, jwtTokenHolder.getRefreshExpiration(), TimeUnit.SECONDS);
        setAuthentication(request, securityUser);
        filterChain.doFilter(request, response);
    }

    /**
     * 将错误信息写入响应
     *
     * @param response   响应对象
     * @param errorEnum  错误信息枚举类
     * @param requestURI 请求路径
     */
    private void writeResponse(HttpServletResponse response, BaseErrorEnum errorEnum, String requestURI) {
        Result<Object> result = Result.of(errorEnum).path(requestURI);
        ServletUtil.write(response, JSONUtil.toJsonStr(result), ContentType.JSON.toString(CharsetUtil.CHARSET_UTF_8));
    }

    /**
     * 设置认证信息
     *
     * @param request     请求对象
     * @param userDetails 用户信息
     */
    private void setAuthentication(HttpServletRequest request, UserDetails userDetails) {
        // 构造 UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        // 设置认证信息，用于后面的过滤器使用
        authentication.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
