package com.github.cadecode.uniboot.framework.security.handler;

import com.github.cadecode.uniboot.common.core.util.TokenUtil;
import com.github.cadecode.uniboot.common.core.web.response.ApiResult;
import com.github.cadecode.uniboot.framework.bean.dto.SysUserDto;
import com.github.cadecode.uniboot.framework.bean.dto.SysUserDto.SysUserDetailsDto;
import com.github.cadecode.uniboot.framework.config.SecurityConfig;
import com.github.cadecode.uniboot.framework.enums.AuthModelEnum;
import com.github.cadecode.uniboot.framework.security.LoginSuccessHandler;
import com.github.cadecode.uniboot.framework.service.SysUserService;
import com.github.cadecode.uniboot.framework.util.SecurityUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
@ConditionalOnProperty(name = "uni-boot.security.auth-model", havingValue = "jwt")
public class JwtLoginSuccessHandler extends LoginSuccessHandler {

    public JwtLoginSuccessHandler(SysUserService sysUserService) {
        super(sysUserService);
    }

    @Override
    public AuthModelEnum getAuthModel() {
        return AuthModelEnum.JWT;
    }

    @Override
    public ApiResult<SysUserDetailsDto> getResult(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 从认证信息中获取用户对象
        SysUserDto.SysUserDetailsDto sysUserDetailsDto = (SysUserDto.SysUserDetailsDto) authentication.getPrincipal();
        // 生成 jwt token
        String jwtToken = TokenUtil.generateToken(sysUserDetailsDto.getId(), sysUserDetailsDto.getUsername(), sysUserDetailsDto.getRoles(),
                SecurityUtil.getExpiration(), SecurityUtil.getSecret());
        // token 放在请求头
        response.addHeader(SecurityUtil.getHeader(), jwtToken);
        return ApiResult.ok(sysUserDetailsDto).path(SecurityConfig.LOGOUT_URL);
    }

}
