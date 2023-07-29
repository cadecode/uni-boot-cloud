package com.github.cadecode.uniboot.framework.svc.controller;

import com.github.cadecode.uniboot.common.core.web.response.ApiResult;
import com.github.cadecode.uniboot.common.plugin.log.annotation.ApiLogger;
import com.github.cadecode.uniboot.common.plugin.log.enums.LogTypeEnum;
import com.github.cadecode.uniboot.framework.api.annotation.ApiFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    private final AuthClient authClient;

    @ApiLogger(type = LogTypeEnum.AUTH, enableSave = true, description = "用户登录")
    @ApiOperation("登录")
    @PostMapping("login")
    public ApiResult<?> login(@RequestParam String username, @RequestParam String password) {
        return authClient.login(username, password);
    }

    @ApiLogger(type = LogTypeEnum.AUTH, enableSave = true, description = "用户注销")
    @ApiOperation("注销")
    @PostMapping("logout")
    public ApiResult<?> logout() {
        return authClient.logout();
    }

    /**
     * 使用 feign 调用 Security login/logout 接口
     * 1. 方便在 swagger 展示和调试
     * 2. 方便对接口进行 log 等监控
     */
    @FeignClient(contextId = "AuthClient", name = "uni-boot-framework")
    public interface AuthClient {

        @PostMapping("login")
        ApiResult<?> login(@RequestParam("username") String username, @RequestParam("password") String password);

        @PostMapping("logout")
        ApiResult<?> logout();
    }
}
