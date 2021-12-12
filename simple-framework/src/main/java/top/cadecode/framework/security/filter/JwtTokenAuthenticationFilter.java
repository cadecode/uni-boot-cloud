package top.cadecode.framework.security.filter;

import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import top.cadecode.common.core.exception.CommonException;
import top.cadecode.common.core.response.CommonResponse;
import top.cadecode.common.enums.AuthErrorEnum;
import top.cadecode.common.util.JsonUtil;
import top.cadecode.common.util.StringUtil;
import top.cadecode.common.util.TokenUtil;
import top.cadecode.common.util.WebUtil;
import top.cadecode.framework.model.entity.SecurityUser;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        // 取出 token
        String token = request.getHeader(tokenUtil.getHeader());
        // token 为空，则不拦截
        if (StringUtil.isEmpty(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        // 校验 Token
        try {
            checkToken(request, response, token);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            writeResponse(response, AuthErrorEnum.TOKEN_ERROR, requestURI);
        }
    }

    private void checkToken(HttpServletRequest request, HttpServletResponse response, String token) throws Exception {
        // 取出 token 内容
        JWTClaimsSet claimsSet = tokenUtil.verifyToken(token);
        // 判断过期时间
        if (claimsSet.getExpirationTime().getTime() < System.currentTimeMillis()) {
            writeResponse(response, AuthErrorEnum.TOKEN_EXPIRED, request.getRequestURI());
            return;
        }
        // 认证通过，设置认证信息
        long id = claimsSet.getLongClaim("id");
        List<String> roles = claimsSet.getStringListClaim("roles");
        SecurityUser securityUser = new SecurityUser();
        securityUser.setId(id);
        securityUser.setAuthorities(roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()));
        // 构造 UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
        // 设置认证信息，用于后面的过滤器使用
        authentication.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void writeResponse(HttpServletResponse response, AuthErrorEnum authErrorEnum, String requestURI) {
        CommonResponse<Object> commonResponse = CommonResponse.of(authErrorEnum).path(requestURI);
        WebUtil.writeJsonToResponse(response, JsonUtil.objToStr(commonResponse));
    }

}
