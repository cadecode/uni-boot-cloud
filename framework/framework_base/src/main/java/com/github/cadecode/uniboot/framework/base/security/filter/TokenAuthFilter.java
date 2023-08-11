package com.github.cadecode.uniboot.framework.base.security.filter;

import com.github.cadecode.uniboot.common.core.extension.strategy.StrategyExecutor;
import com.github.cadecode.uniboot.framework.base.config.SecurityConfig.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Token 校验过滤器，继承 OncePerRequestFilter
 *
 * @author Cade Li
 * @date 2022/5/28
 */
@RequiredArgsConstructor
@Component
public class TokenAuthFilter extends OncePerRequestFilter {

    private final StrategyExecutor strategyExecutor;

    private final SecurityProperties securityProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        strategyExecutor.execute(TokenAuthFilterService.class, securityProperties::getAuthModel, s -> {
            try {
                s.filter(request, response, filterChain);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
