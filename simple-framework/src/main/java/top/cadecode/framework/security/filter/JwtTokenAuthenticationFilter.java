package top.cadecode.framework.security.filter;

import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import top.cadecode.common.core.response.CommonResponse;
import top.cadecode.common.enums.AuthErrorEnum;
import top.cadecode.common.util.JsonUtil;
import top.cadecode.common.util.StringUtil;
import top.cadecode.common.util.TokenUtil;
import top.cadecode.common.util.WebUtil;
import top.cadecode.framework.model.entity.SecurityUser;
import top.cadecode.framework.model.mapper.SecurityUserMapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Cade Li
 * @date 2021/12/12
 * @description JWT Token 校验过滤器
 */
@Component
@RequiredArgsConstructor
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenUtil tokenUtil;
    private final SecurityUserMapper securityUserMapper;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 获取请求地址
        String requestURI = request.getRequestURI();
        // 取出 token
        String token = request.getHeader(tokenUtil.getHeader());
        // 判断 token 是否为空，为空则不拦截
        if (StringUtil.isEmpty(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        // 声明一个 SecurityUser，用于设置权限
        SecurityUser securityUser;
        // 校验 Token
        try {
            // 取出 token 内容
            JWTClaimsSet claimsSet = tokenUtil.verifyToken(token);
            long id = tokenUtil.getIdFromClaims(claimsSet);
            String name = tokenUtil.getNameFromClaims(claimsSet);
            List<String> roles = tokenUtil.getRolesFromClaims(claimsSet);
            // 判断 token 过期时间
            if (!tokenUtil.isExpired(claimsSet.getExpirationTime())) {
                // 如果 token 没有过期
                // 根据 token 内容绑定 securityUser
                securityUser = new SecurityUser();
                securityUser.setId(id);
                securityUser.setUsername(name);
                securityUser.setAuthorities(roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));
                setAuthentication(request, securityUser);
                filterChain.doFilter(request, response);
                return;
            }
            // 如果 token 已过期
            String refreshHeader = request.getHeader(tokenUtil.getRefreshHeader());
            // 判断 refreshToken 是否为空
            if (StringUtil.isEmpty(refreshHeader)) {
                writeResponse(response, AuthErrorEnum.TOKEN_EXPIRED, requestURI);
                return;
            }
            // 根据 username 查询用户
            securityUser = (SecurityUser) userDetailsService.loadUserByUsername(name);
            // 判断 refreshToken 是否相符
            if (securityUser == null || !refreshHeader.equals(securityUser.getRefreshToken())) {
                writeResponse(response, AuthErrorEnum.TOKEN_REFRESH_ERROR, requestURI);
                return;
            }
            // 判断 refreshToken 是否过期
            if (securityUser.getTokenTime() == null
                    || tokenUtil.isExpired(securityUser.getTokenTime(), tokenUtil.getRefreshExpiration())) {
                writeResponse(response, AuthErrorEnum.TOKEN_REFRESH_EXPIRED, requestURI);
                return;
            }
            // 重颁发 token 并设置响应头
            List<String> newRoles = securityUser.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            String newToken = tokenUtil.generateToken(securityUser.getId(), securityUser.getUsername(), newRoles);
            String newRefreshToken = UUID.randomUUID().toString();
            response.addHeader(tokenUtil.getHeader(), newToken);
            response.addHeader(tokenUtil.getRefreshHeader(), newRefreshToken);
            // 更新 refresh token
            securityUserMapper.updateSecurityUserToken(securityUser.getId(), newRefreshToken);
            setAuthentication(request, securityUser);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            writeResponse(response, AuthErrorEnum.TOKEN_ERROR, requestURI);
        }
    }

    private void writeResponse(HttpServletResponse response, AuthErrorEnum authErrorEnum, String requestURI) {
        CommonResponse<Object> commonResponse = CommonResponse.of(authErrorEnum).path(requestURI);
        WebUtil.writeJsonToResponse(response, JsonUtil.objToStr(commonResponse));
    }

    private void setAuthentication(HttpServletRequest request, UserDetails userDetails) {
        // 构造 UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        // 设置认证信息，用于后面的过滤器使用
        authentication.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
