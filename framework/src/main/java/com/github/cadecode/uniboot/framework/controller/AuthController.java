package com.github.cadecode.uniboot.framework.controller;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.github.cadecode.uniboot.common.core.util.JacksonUtil;
import com.github.cadecode.uniboot.common.core.web.response.ApiResult;
import com.github.cadecode.uniboot.common.plugin.log.annotation.ApiLogger;
import com.github.cadecode.uniboot.common.plugin.log.enums.LogTypeEnum;
import com.github.cadecode.uniboot.framework.annotation.ApiFormat;
import com.github.cadecode.uniboot.framework.config.SecurityConfig;
import com.github.cadecode.uniboot.framework.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@Api(tags = "登录认证")
@RestController
@RequestMapping("auth")
public class AuthController {

    /**
     * 复用Security login接口，方便swagger展示
     */
    @ApiLogger(type = LogTypeEnum.AUTH, enableSave = true, description = "用户登录")
    @ApiOperation("登录")
    @PostMapping("login")
    public ApiResult<?> login(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam String username, @RequestParam String password) {
        String replacedURL = request.getRequestURL().toString().replace("/auth/login", SecurityConfig.LOGIN_URL);
        HttpResponse loginRes = HttpUtil.createPost(replacedURL)
                .form(SecurityConfig.USERNAME_PARAMETER, username)
                .form(SecurityConfig.PASSWORD_PARAMETER, password)
                .execute();
        response.addHeader(SecurityUtil.getHeader(), loginRes.header(SecurityUtil.getHeader()));
        return JacksonUtil.toBean(loginRes.body(), ApiResult.class);
    }

    /**
     * 复用Security logout接口，方便swagger展示
     */
    @ApiLogger(type = LogTypeEnum.AUTH, enableSave = true, description = "用户注销")
    @ApiOperation("注销")
    @PostMapping("logout")
    public ApiResult<?> logout(HttpServletRequest request, HttpServletResponse response) {
        String replacedURL = request.getRequestURL().toString().replace("/auth/logout", SecurityConfig.LOGOUT_URL);
        HttpResponse loginRes = HttpUtil.createPost(replacedURL)
                .header(SecurityUtil.getHeader(), request.getHeader(SecurityUtil.getHeader()))
                .execute();
        return JacksonUtil.toBean(loginRes.body(), ApiResult.class);
    }
}
