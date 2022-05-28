package top.cadecode.sra.framework.security.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import top.cadecode.sra.common.enums.AuthModelEnum;
import top.cadecode.sra.framework.security.JwtTokenHolder;
import top.cadecode.sra.framework.security.TokenAuthFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Cade Li
 * @date 2022/5/28
 * @description JWT 模式校验过滤器
 */
@RequiredArgsConstructor
@Component
public class JwtTokenAuthFilter extends TokenAuthFilter {

    private final JwtTokenHolder jwtTokenHolder;

    @Override
    public AuthModelEnum getAuthModel() {
        return AuthModelEnum.JWT;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    }
}
