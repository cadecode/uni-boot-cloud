package com.github.cadecode.uniboot.framework.base.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.github.cadecode.uniboot.common.core.extension.strategy.StrategyExecutor;
import com.github.cadecode.uniboot.framework.api.enums.AuthModelEnum;
import com.github.cadecode.uniboot.framework.base.config.SecurityConfig.SecurityProperties;
import com.github.cadecode.uniboot.framework.base.security.filter.CorsAllowAnyFilter;
import com.github.cadecode.uniboot.framework.base.security.filter.TokenAuthFilter;
import com.github.cadecode.uniboot.framework.base.security.filter.TraceInfoFilter;
import com.github.cadecode.uniboot.framework.base.security.handler.NoAuthenticationHandler;
import com.github.cadecode.uniboot.framework.base.security.handler.NoAuthorityHandler;
import com.github.cadecode.uniboot.framework.base.security.voter.DataBaseRoleVoter;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity.IgnoredRequestConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Security 配置
 *
 * @author Cade Li
 * @date 2022/5/27
 */
@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(SecurityProperties.class)
@ConditionalOnMissingBean(SecurityConfig.class)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter implements InitializingBean {

    /**
     * 登录路径
     */
    public static final String LOGIN_URL = "/login";

    /**
     * 注销路径
     */
    public static final String LOGOUT_URL = "/logout";

    private final StrategyExecutor strategyExecutor;

    /**
     * 配置项
     */
    @Getter
    private final SecurityProperties properties;

    /**
     * 注入各种处理器
     */
    private final NoAuthenticationHandler noAuthenticationHandler;
    private final NoAuthorityHandler noAuthorityHandler;

    /**
     * 注入投票器
     */
    private final DataBaseRoleVoter dataBaseRoleVoter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭 csrf
        http.csrf().disable();
        // 关闭 session 管理
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        configAuthorize(http);
        registerFilter(http);
        log.info("Config Security over，AuthModel:{}", properties.getAuthModel());
    }

    /**
     * 配置鉴权规则
     */
    private void configAuthorize(HttpSecurity http) throws Exception {
        // 配置鉴权规则
        List<String> permitAllList = properties.getPermitAllList();
        log.info("Config Security permit all list:{}", permitAllList);
        http.authorizeRequests()
                // 尝试请求直接通过
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(ArrayUtil.toArray(permitAllList, String.class)).permitAll()
                .anyRequest().authenticated();
        // 配置异常处理
        http.exceptionHandling()
                // 配置未登录处理器
                .authenticationEntryPoint(noAuthenticationHandler)
                // 配置无权限处理器
                .accessDeniedHandler(noAuthorityHandler);
        // 自定义的 accessDecisionManager
        http.authorizeRequests()
                .accessDecisionManager(new UnanimousBased(
                        Arrays.asList(new WebExpressionVoter(), dataBaseRoleVoter)));
    }

    /**
     * 配置 filter
     */
    private void registerFilter(HttpSecurity http) {
        // 配置 Token 校验过滤器
        http.addFilterBefore(new TokenAuthFilter(strategyExecutor, properties), LogoutFilter.class);
        // 配置 trace id 过滤器
        http.addFilterBefore(new TraceInfoFilter(), TokenAuthFilter.class);
        // 配置跨域过滤器
        http.addFilterBefore(new CorsAllowAnyFilter(), TraceInfoFilter.class);
    }

    @Override
    public void configure(WebSecurity web) {
        // 忽略配置器
        IgnoredRequestConfigurer ignoring = web.ignoring();
        // 设置忽略的路径
        List<String> ignoringMatcherList = properties.getIgnoringMatcherList();
        log.info("Config Security ignoring matcher list:{}", ignoringMatcherList);
        ignoring.antMatchers(ArrayUtil.toArray(ignoringMatcherList, String.class));
    }

    @Override
    public void afterPropertiesSet() {
        // 对 SecurityProperties 配置项进行修改
        // 加入忽略 url list
        this.getProperties().getIgnoringMatcherList().addAll(CollUtil.newArrayList(
                // 放行 swagger knife 文档
                "/doc.html", "/webjars/**", "/swagger-resources/**", "/v2/api-docs/**",
                // 放行其他框架
                "/error", "/druid/**", "/actuator/**"
        ));
    }

    /**
     * Security 配置项
     */
    @Data
    @ConfigurationProperties("uni-boot.security")
    public static class SecurityProperties {

        /**
         * 鉴权模式
         */
        private AuthModelEnum authModel;

        /**
         * permitAll url list
         */
        private List<String> permitAllList = new ArrayList<>();

        /**
         * 忽略鉴权的 url list
         */
        private List<String> ignoringMatcherList = new ArrayList<>();

        /**
         * JWT Token 配置
         */
        private TokenConfig tokenConfig;

    }

    /**
     * JWT 配置项
     */
    @Data
    public static class TokenConfig {

        /**
         * token 过期时间，单位秒
         */
        private Long expiration;

        /**
         * 密钥
         */
        private String secret;

        /**
         * 单个账号最多允许几个 token（大于 0）
         * 为 1 时即不允许多次登录同时在线
         */
        private Integer maxCount = 1;

    }
}
