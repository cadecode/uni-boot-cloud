package com.github.cadecode.uniboot.framework.svc.security;

import com.github.cadecode.uniboot.common.core.extension.strategy.StrategyContext;
import com.github.cadecode.uniboot.common.core.util.TokenUtil;
import com.github.cadecode.uniboot.common.core.web.response.ApiResult;
import com.github.cadecode.uniboot.framework.api.consts.HttpConst;
import com.github.cadecode.uniboot.framework.api.enums.AuthModelEnum;
import com.github.cadecode.uniboot.framework.base.security.model.SysUserDetails;
import com.github.cadecode.uniboot.framework.base.util.SecurityUtil;
import com.github.cadecode.uniboot.framework.svc.config.FrameSecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录成功处理器
 *
 * @author Cade Li
 * @date 2021/12/11
 */
@Component
public class JwtLoginSuccessHandleService extends LoginSuccessHandleService {

    @Override
    public ApiResult<SysUserDetails> getResult(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 从认证信息中获取用户对象
        SysUserDetails sysUserDetails = (SysUserDetails) authentication.getPrincipal();
        // 生成 jwt token
        String jwtToken = TokenUtil.generateToken(sysUserDetails.getId(), sysUserDetails.getUsername(), sysUserDetails.getRoles(),
                SecurityUtil.getTokenExpiration(), SecurityUtil.getTokenSecret());
        // token 放在请求头
        response.addHeader(HttpConst.HEAD_TOKEN, jwtToken);
        return ApiResult.ok(sysUserDetails).path(FrameSecurityConfig.LOGOUT_URL);
    }

    @Override
    public boolean supports(StrategyContext delimiter) {
        return delimiter.getStrategyType() == AuthModelEnum.JWT;
    }
}
