package com.github.cadecode.uniboot.framework.svc.config;

import com.github.cadecode.uniboot.framework.api.config.SecurityConfig;
import com.github.cadecode.uniboot.framework.api.security.filter.TokenAuthFilter;
import com.github.cadecode.uniboot.framework.api.security.handler.NoAuthenticationHandler;
import com.github.cadecode.uniboot.framework.api.security.handler.NoAuthorityHandler;
import com.github.cadecode.uniboot.framework.api.security.voter.DataBaseRoleVoter;
import com.github.cadecode.uniboot.framework.svc.security.LoginFailureHandler;
import com.github.cadecode.uniboot.framework.svc.security.LoginSuccessHandler;
import com.github.cadecode.uniboot.framework.svc.security.SignOutSuccessHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity.IgnoredRequestConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * SecurityConfig 补充配置
 * 登录认证相关配置
 *
 * @author Cade Li
 * @since 2023/7/28
 */
@Slf4j
@Configuration
public class FrameSecurityConfig extends SecurityConfig {

    /**
     * 登录参数
     */
    public static final String USERNAME_PARAMETER = "username";
    public static final String PASSWORD_PARAMETER = "password";

    /**
     * 登录路径
     */
    public static final String LOGIN_URL = "/login";

    /**
     * 注销路径
     */
    public static final String LOGOUT_URL = "/logout";

    /**
     * 注入各种处理器
     */
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;
    private final SignOutSuccessHandler signOutSuccessHandler;

    /**
     * 注入 UserDetailsService
     */
    private final UserDetailsService userDetailsService;

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    public FrameSecurityConfig(SecurityProperties properties, NoAuthenticationHandler noAuthenticationHandler,
                               NoAuthorityHandler noAuthorityHandler, TokenAuthFilter tokenAuthFilter,
                               DataBaseRoleVoter dataBaseRoleVoter, LoginSuccessHandler loginSuccessHandler,
                               LoginFailureHandler loginFailureHandler, SignOutSuccessHandler signOutSuccessHandler,
                               UserDetailsService userDetailsService) {
        super(properties, noAuthenticationHandler, noAuthorityHandler, tokenAuthFilter, dataBaseRoleVoter);
        this.loginSuccessHandler = loginSuccessHandler;
        this.loginFailureHandler = loginFailureHandler;
        this.signOutSuccessHandler = signOutSuccessHandler;
        this.userDetailsService = userDetailsService;
    }

    /**
     * 密码加密器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PASSWORD_ENCODER;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(PASSWORD_ENCODER);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        // 配置注销处理器
        http.logout().permitAll()
                .logoutUrl(LOGOUT_URL)
                .logoutSuccessHandler(signOutSuccessHandler);
        // 配置登录处理器
        http.formLogin().permitAll()
                .loginProcessingUrl(LOGIN_URL)
                .usernameParameter(USERNAME_PARAMETER)
                .passwordParameter(PASSWORD_PARAMETER)
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler);
        log.info("Config Security login url:{}, logout url:{}", LOGIN_URL, LOGOUT_URL);
    }

    @Override
    public void configure(WebSecurity web) {
        super.configure(web);
        IgnoredRequestConfigurer ignoring = web.ignoring();
        // 放行登录接口
        ignoring.antMatchers("/auth/login");
        // 放行接口权限表查询接口
        ignoring.antMatchers("/system/api/list_roles_vo");
    }
}
