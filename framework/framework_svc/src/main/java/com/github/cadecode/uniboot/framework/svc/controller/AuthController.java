package com.github.cadecode.uniboot.framework.svc.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import com.github.cadecode.uniboot.common.core.util.JacksonUtil;
import com.github.cadecode.uniboot.common.core.web.response.ApiResult;
import com.github.cadecode.uniboot.common.plugin.log.annotation.ApiLogger;
import com.github.cadecode.uniboot.common.plugin.log.enums.LogTypeEnum;
import com.github.cadecode.uniboot.framework.api.annotation.ApiFormat;
import com.github.cadecode.uniboot.framework.api.consts.SecurityConst;
import com.github.cadecode.uniboot.framework.svc.config.FrameSecurityConfig;
import feign.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    public ApiResult<?> login(HttpServletResponse sResponse, @RequestParam String username, @RequestParam String password) throws IOException {
        // 从 rpc 调用中获取 header token 设置到响应
        Response rResponse = authClient.login(username, password);
        String token = rResponse.headers().get(SecurityConst.HEAD_TOKEN).stream().findFirst().get();
        sResponse.addHeader(SecurityConst.HEAD_TOKEN, token);
        // 获取 rpc 结果
        String body = IoUtil.read(rResponse.body().asReader(CharsetUtil.CHARSET_UTF_8));
        return JacksonUtil.toBean(body, ApiResult.class);
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

        @PostMapping(FrameSecurityConfig.LOGIN_URL)
        Response login(@RequestParam("username") String username, @RequestParam("password") String password);

        @PostMapping(FrameSecurityConfig.LOGOUT_URL)
        ApiResult<?> logout();
    }
}
