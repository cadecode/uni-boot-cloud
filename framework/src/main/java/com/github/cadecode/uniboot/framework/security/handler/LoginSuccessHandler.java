package com.github.cadecode.uniboot.framework.security.handler;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import com.github.cadecode.uniboot.common.core.extension.strategy.StrategyExecutor;
import com.github.cadecode.uniboot.common.core.util.JacksonUtil;
import com.github.cadecode.uniboot.common.core.web.response.ApiResult;
import com.github.cadecode.uniboot.framework.bean.dto.SysUserDto;
import com.github.cadecode.uniboot.framework.bean.dto.SysUserDto.SysUserDetailsDto;
import com.github.cadecode.uniboot.framework.bean.po.SysUser;
import com.github.cadecode.uniboot.framework.config.SecurityConfig.SecurityProperties;
import com.github.cadecode.uniboot.framework.service.SysUserService;
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
     * @param sysUserDetailsDto 系统用户 DTO
     */
    public void updateLoginInfo(SysUserDto.SysUserDetailsDto sysUserDetailsDto) {
        sysUserDetailsDto.setLoginIp(NetUtil.getLocalhost().getHostAddress());
        sysUserDetailsDto.setLoginDate(new Date());
        // 同步到用户表
        sysUserService.lambdaUpdate()
                .eq(SysUser::getId, sysUserDetailsDto.getId())
                .set(SysUser::getLoginIp, sysUserDetailsDto.getLoginIp())
                .set(SysUser::getLoginDate, sysUserDetailsDto.getLoginDate())
                .update();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        ApiResult<SysUserDetailsDto> result = strategyExecutor.execute(LoginSuccessHandleService.class, securityProperties::getAuthModel, s -> {
            return s.getResult(request, response, authentication);
        });
        updateLoginInfo(result.getData());
        ServletUtil.write(response, JacksonUtil.toJson(result), ContentType.JSON.toString(CharsetUtil.CHARSET_UTF_8));
    }
}
