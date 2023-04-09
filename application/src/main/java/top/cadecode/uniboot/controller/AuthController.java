package top.cadecode.uniboot.controller;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.cadecode.uniboot.common.annotation.ApiFormat;
import top.cadecode.uniboot.common.response.ApiResult;
import top.cadecode.uniboot.common.util.JacksonUtil;
import top.cadecode.uniboot.framework.config.SecurityConfig;
import top.cadecode.uniboot.framework.security.TokenAuthHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证API
 *
 * @author Cade Li
 * @since 2023/4/9
 */
@ApiFormat
@Slf4j
@RequiredArgsConstructor
@Api(tags = "认证API")
@RestController
@RequestMapping("auth")
public class AuthController {

    private final TokenAuthHolder tokenAuthHolder;

    /**
     * 复用Security login接口，方便swagger展示
     */
    @ApiOperation("登录")
    @PostMapping("login")
    public ApiResult<?> login(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam String username, @RequestParam String password) {
        String replacedURL = request.getRequestURL().toString().replace("/auth/login", SecurityConfig.LOGIN_URL);
        HttpResponse loginRes = HttpUtil.createPost(replacedURL)
                .form(SecurityConfig.USERNAME_PARAMETER, username)
                .form(SecurityConfig.PASSWORD_PARAMETER, password)
                .execute();
        response.addHeader(tokenAuthHolder.getHeader(), loginRes.header(tokenAuthHolder.getHeader()));
        return JacksonUtil.toBean(loginRes.body(), ApiResult.class);
    }

    /**
     * 复用Security logout接口，方便swagger展示
     */
    @ApiOperation("注销")
    @PostMapping("logout")
    public ApiResult<?> login(HttpServletRequest request, HttpServletResponse response) {
        String replacedURL = request.getRequestURL().toString().replace("/auth/logout", SecurityConfig.LOGOUT_URL);
        HttpResponse loginRes = HttpUtil.createPost(replacedURL)
                .header(tokenAuthHolder.getHeader(), request.getHeader(tokenAuthHolder.getHeader()))
                .execute();
        return JacksonUtil.toBean(loginRes.body(), ApiResult.class);
    }
}
