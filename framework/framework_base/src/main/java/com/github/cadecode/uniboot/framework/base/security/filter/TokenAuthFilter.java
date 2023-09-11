package com.github.cadecode.uniboot.framework.base.security.filter;

import com.github.cadecode.uniboot.common.core.extension.strategy.StrategyExecutor;
import com.github.cadecode.uniboot.framework.base.config.SecurityConfig.SecurityProperties;
import com.github.cadecode.uniboot.framework.base.security.strategy.TokenAuthStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Filter, token auth
 *
 * @author Cade Li
 * @since 2023/9/11
 */
@RequiredArgsConstructor
public class TokenAuthFilter extends OncePerRequestFilter {

    private final StrategyExecutor strategyExecutor;

    private final SecurityProperties securityProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        strategyExecutor.execute(TokenAuthStrategy.class, securityProperties::getAuthModel, s -> {
            try {
                s.filter(request, response, filterChain);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
