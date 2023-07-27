package com.github.cadecode.uniboot.framework.api.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.github.cadecode.uniboot.framework.api.config.SecurityConfig.SecurityProperties;
import com.github.cadecode.uniboot.framework.api.enums.AuthModelEnum;
import com.github.cadecode.uniboot.framework.api.security.filter.TokenAuthFilter;
import com.github.cadecode.uniboot.framework.api.security.handler.*;
import com.github.cadecode.uniboot.framework.api.security.voter.DataBaseRoleVoter;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity.IgnoredRequestConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

/**
 * Security 配置
 *
 * @author Cade Li
 * @date 2022/5/27
 */
@Slf4j
@Data
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(SecurityProperties.class)
@Configuration
public class SecurityConfig {

    /**
     * 登录路径
     */
    public static final String LOGIN_URL = "/login";

    /**
     * 登录参数
     */
    public static final String USERNAME_PARAMETER = "username";
    public static final String PASSWORD_PARAMETER = "password";

    /**
     * 注销路径
     */
    public static final String LOGOUT_URL = "/logout";

    /**
     * 配置项
     */
    private final SecurityProperties properties;

    /**
     * 注入各种处理器
     */
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;
    private final NoAuthenticationHandler noAuthenticationHandler;
    private final NoAuthorityHandler noAuthorityHandler;
    private final SignOutSuccessHandler signOutSuccessHandler;

    /**
     * 注入 Token 过滤器
     */
    private final TokenAuthFilter tokenAuthFilter;

    /**
     * 注入投票器
     */
    private final DataBaseRoleVoter dataBaseRoleVoter;

    /**
     * 注入 UserDetailsService
     */
    private final UserDetailsService userDetailsService;

    /**
     * 密码加密器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Security 配置
     */
    @Bean
    public WebSecurityConfigurerAdapter webSecurityConfigurer(PasswordEncoder passwordEncoder) {
        return new WebSecurityConfigurerAdapter() {

            @Override
            protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
            }

            @Override
            protected void configure(HttpSecurity http) throws Exception {
                // 关闭 csrf
                http.csrf().disable();
                // 关闭 session 管理
                http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                // 配置鉴权规则
                http.authorizeRequests()
                        // 尝试请求直接通过
                        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().authenticated();
                // 配置注销处理器
                http.logout().permitAll()
                        .logoutUrl(LOGOUT_URL)
                        .logoutSuccessHandler(signOutSuccessHandler);
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
                // 配置登录处理器
                http.formLogin().permitAll()
                        .loginProcessingUrl(LOGIN_URL)
                        .usernameParameter(USERNAME_PARAMETER)
                        .passwordParameter(PASSWORD_PARAMETER)
                        .successHandler(loginSuccessHandler)
                        .failureHandler(loginFailureHandler);
                // 配置 Token 校验过滤器
                http.addFilterBefore(tokenAuthFilter, UsernamePasswordAuthenticationFilter.class);
                log.info("Config Security over，AuthModel:{}", properties.getAuthModel());
            }

            @Override
            public void configure(WebSecurity web) {
                // 忽略配置器
                IgnoredRequestConfigurer ignoring = web.ignoring();
                // 放行 swagger knife 文档
                ignoring.antMatchers("/doc.html", "/webjars/**", "/swagger-resources/**", "/v2/api-docs/**");
                // 放行其他框架
                ignoring.antMatchers("/error", "/druid/**", "/actuator/**");
                // 设置忽略的路径
                List<String> ignoreUrls = properties.getIgnoreUrls();
                if (CollUtil.isNotEmpty(ignoreUrls)) {
                    log.info("Config Security ignore urls:{}", ignoreUrls);
                    ignoring.antMatchers(ArrayUtil.toArray(ignoreUrls, String.class));
                }
            }
        };
    }

    /**
     * Security 配置
     */
    @Data
    @ConfigurationProperties("uni-boot.security")
    public static class SecurityProperties {

        /**
         * 鉴权模式
         */
        private AuthModelEnum authModel;

        /**
         * 忽略鉴权的 url
         */
        private List<String> ignoreUrls;

        /**
         * JWT Token 配置
         */
        private TokenConfig token;

    }

    /**
     * JWT 配置类
     */
    @Data
    public static class TokenConfig {

        /**
         * token 请求头字段
         */
        private String header;

        /**
         * token 过期时间，单位秒
         */
        private Long expiration;

        /**
         * 密钥
         */
        private String secret;
    }
}
