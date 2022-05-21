package top.cadecode.sra.framework.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.cadecode.sra.framework.security.filter.JwtTokenAuthenticationFilter;
import top.cadecode.sra.framework.security.handler.*;
import top.cadecode.sra.framework.security.voter.DbRoleVoter;

import java.util.Arrays;

/**
 * @author Cade Li
 * @date 2021/12/9
 * @description Spring Security 配置类
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String LOGIN_URL = "/login";
    public static final String LOGOUT_URL = "/logout";

    private final UserDetailsService userDetailsService;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;
    private final SignOutSuccessHandler signOutSuccessHandler;
    private final NoAuthenticationHandler noAuthenticationHandler;
    private final NoAuthorityHandler noAuthorityHandler;
    private final JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;
    private final DbRoleVoter dbRoleVoter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭 csrf 和 session
        http.csrf().disable();
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                // 尝试请求直接通过
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .loginProcessingUrl(LOGIN_URL)
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                .and()
                .logout().permitAll()
                .logoutUrl(LOGOUT_URL)
                .logoutSuccessHandler(signOutSuccessHandler)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(noAuthenticationHandler)
                .accessDeniedHandler(noAuthorityHandler)
                .and()
                // 自定义的 accessDecisionManager
                .authorizeRequests()
                .accessDecisionManager(accessDecisionManager());
        // 注册 JWT 校验过滤器
        http.addFilterBefore(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 配置投票器管理器
     * UnanimousBased，全部投票器通过才算通过
     *
     * @return 投票器管理器
     */
    private AccessDecisionManager accessDecisionManager() {
        return new UnanimousBased(Arrays.asList(
                new WebExpressionVoter(),
                dbRoleVoter
        ));
    }
}
