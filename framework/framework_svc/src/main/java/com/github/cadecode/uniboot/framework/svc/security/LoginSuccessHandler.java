package com.github.cadecode.uniboot.framework.svc.security;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import com.github.cadecode.uniboot.common.core.extension.strategy.StrategyExecutor;
import com.github.cadecode.uniboot.common.core.util.JacksonUtil;
import com.github.cadecode.uniboot.common.core.web.response.ApiResult;
import com.github.cadecode.uniboot.framework.base.config.SecurityConfig.SecurityProperties;
import com.github.cadecode.uniboot.framework.base.security.model.SysUserDetails;
import com.github.cadecode.uniboot.framework.svc.bean.po.SysUser;
import com.github.cadecode.uniboot.framework.svc.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 登录成功处理器抽象
 *
 * @author Cade Li
 * @date 2022/5/28
 */
@RequiredArgsConstructor
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final StrategyExecutor strategyExecutor;

    private final SecurityProperties securityProperties;

    private final SysUserService sysUserService;

    /**
     * 更新登录信息
     *
     * @param sysUserDetails 系统用户 DTO
     */
    public void updateLoginInfo(SysUserDetails sysUserDetails) {
        sysUserDetails.setLoginIp(NetUtil.getLocalhost().getHostAddress());
        sysUserDetails.setLoginDate(new Date());
        // 同步到用户表
        sysUserService.lambdaUpdate()
                .eq(SysUser::getId, sysUserDetails.getId())
                .set(SysUser::getLoginIp, sysUserDetails.getLoginIp())
                .set(SysUser::getLoginDate, sysUserDetails.getLoginDate())
                .update();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        ApiResult<SysUserDetails> result = strategyExecutor.execute(LoginSuccessHandleService.class, securityProperties::getAuthModel, s -> {
            return s.getResult(request, response, authentication);
        });
        updateLoginInfo(result.getData());
        ServletUtil.write(response, JacksonUtil.toJson(result), ContentType.JSON.toString(CharsetUtil.CHARSET_UTF_8));
    }
}
