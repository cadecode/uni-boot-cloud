package top.cadecode.sra.framework.security.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import top.cadecode.sra.common.enums.AuthModelEnum;
import top.cadecode.sra.framework.security.TokenAuthFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Cade Li
 * @date 2022/5/28
 * @description Redis + Token 模式校验过滤器
 */
@RequiredArgsConstructor
@Component
public class RedisTokenAuthFilter extends TokenAuthFilter {

    private final RedisTemplate<String, ?> redisTemplate;

    @Override
    public AuthModelEnum getAuthModel() {
        return AuthModelEnum.REDIS;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    }
}
