package com.github.cadecode.uniboot.framework.svc.security;

import com.github.cadecode.uniboot.common.core.extension.strategy.StrategyContext;
import com.github.cadecode.uniboot.common.core.web.response.ApiResult;
import com.github.cadecode.uniboot.common.plugin.cache.util.KeyGeneUtil;
import com.github.cadecode.uniboot.common.plugin.cache.util.RedisUtil;
import com.github.cadecode.uniboot.framework.api.consts.HttpConst;
import com.github.cadecode.uniboot.framework.api.consts.KeyPrefix;
import com.github.cadecode.uniboot.framework.api.enums.AuthModelEnum;
import com.github.cadecode.uniboot.framework.base.security.model.SysUserDetails;
import com.github.cadecode.uniboot.framework.base.util.SecurityUtil;
import com.github.cadecode.uniboot.framework.svc.config.FrameSecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * 登录成功处理器
 *
 * @author Cade Li
 * @date 2021/12/11
 */
@Component
public class RedisLoginSuccessHandleService extends LoginSuccessHandleService {

    @Override
    public ApiResult<SysUserDetails> getResult(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 从认证信息中获取用户对象
        SysUserDetails sysUserDetails = (SysUserDetails) authentication.getPrincipal();
        // 生成 uuid token
        String uuidToken = SecurityUtil.generateUUID();
        // token 放在请求头
        response.addHeader(HttpConst.HEAD_TOKEN, uuidToken);
        // 生成存放登录信息的 redis key
        String loginUserKey = KeyGeneUtil.key(KeyPrefix.LOGIN_USER, uuidToken);
        RedisUtil.set(loginUserKey, sysUserDetails, SecurityUtil.getExpiration(), TimeUnit.SECONDS);
        return ApiResult.ok(sysUserDetails).path(FrameSecurityConfig.LOGOUT_URL);
    }

    @Override
    public boolean supports(StrategyContext delimiter) {
        return delimiter.getStrategyType() == AuthModelEnum.REDIS;
    }
}
