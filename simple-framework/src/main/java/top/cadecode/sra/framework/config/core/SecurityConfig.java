package top.cadecode.sra.framework.config.core;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity.IgnoredRequestConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.cadecode.sra.common.enums.AuthModelEnum;
import top.cadecode.sra.framework.security.TokenAuthFilter;
import top.cadecode.sra.framework.security.handler.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Cade Li
 * @date 2022/5/27
 * @description Security 配置
 */
@Slf4j
@Data
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
@ConfigurationProperties("sra.security")
@ConditionalOnProperty(name = "sra.config.security-on", havingValue = "true")
public class SecurityConfig {

    /**
     * 鉴权模式
     */
    private AuthModelEnum authModel;

    /**
     * 忽略鉴权的 url
     */
    private List<String> ignoreUrls;

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
    private final NoAuthenticationHandler noAuthenticationHandler;
    private final NoAuthorityHandler noAuthorityHandler;
    private final SignOutSuccessHandler signOutSuccessHandler;

    /**
     * 注入 Token 过滤器
     */
    private final List<TokenAuthFilter> tokenAuthFilters;

    /**
     * security 配置
     */
    @Bean
    public WebSecurityConfigurerAdapter webSecurityConfigurer() {
        return new WebSecurityConfigurerAdapter() {
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
                // 配置登录处理器
                http.formLogin().permitAll()
                        .loginProcessingUrl(LOGIN_URL)
                        .successHandler(loginSuccessHandler)
                        .failureHandler(loginFailureHandler);
                // 配置注销处理器
                http.logout().permitAll()
                        .logoutUrl(LOGOUT_URL)
                        .logoutSuccessHandler(signOutSuccessHandler);
                // 配置异常处理
                http.exceptionHandling()
                        // 配置未登录处理器
                        .authenticationEntryPoint(noAuthenticationHandler)
                        // 配置无权限处理器
                        .accessDeniedHandler(noAuthorityHandler)
                        .and()
                        // 自定义的 accessDecisionManager
                        .authorizeRequests()
                        .accessDecisionManager(new UnanimousBased(Arrays.asList(new WebExpressionVoter())));
                // 根据认证模式配置过滤器
                if (Objects.isNull(authModel)) {
                    authModel = AuthModelEnum.JWT;
                    log.info("没有配置认证模式，默认为 {}", authModel);
                }
                // 配置 Token 校验过滤器
                List<TokenAuthFilter> filters = tokenAuthFilters.stream()
                        .filter(o -> o.getAuthModel() == authModel)
                        .collect(Collectors.toList());
                if (!filters.isEmpty()) {
                    http.addFilterBefore(filters.get(0), UsernamePasswordAuthenticationFilter.class);
                    log.info("完成 Security 配置，认证模式 {}", authModel);
                }
            }

            @Override
            public void configure(WebSecurity web) {
                // 忽略配置器
                IgnoredRequestConfigurer ignoring = web.ignoring();
                // 放行 swagger knife 文档
                ignoring.antMatchers("/doc.html", "/webjars/**", "/swagger-resources/**", "/v2/api-docs/**");
                // 设置忽略的路径
                if (CollUtil.isNotEmpty(ignoreUrls)) {
                    log.info("配置 Security 放行 {}", ignoreUrls);
                    ignoring.antMatchers(ArrayUtil.toArray(ignoreUrls, String.class));
                }
            }
        };
    }

    /**
     * 密码加密器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
