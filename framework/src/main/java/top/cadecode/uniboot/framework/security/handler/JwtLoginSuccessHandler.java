package top.cadecode.uniboot.framework.security.handler;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import top.cadecode.uniboot.common.core.enums.AuthModelEnum;
import top.cadecode.uniboot.common.core.response.ApiResult;
import top.cadecode.uniboot.framework.bean.dto.SysUserDto;
import top.cadecode.uniboot.framework.bean.dto.SysUserDto.SysUserDetailsDto;
import top.cadecode.uniboot.framework.config.SecurityConfig;
import top.cadecode.uniboot.framework.security.LoginSuccessHandler;
import top.cadecode.uniboot.framework.security.TokenAuthHolder;
import top.cadecode.uniboot.framework.service.SysUserService;

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

    /**
     * Token 处理器
     */
    private final TokenAuthHolder tokenAuthHolder;

    public JwtLoginSuccessHandler(TokenAuthHolder tokenAuthHolder, SysUserService sysUserService) {
        super(sysUserService);
        this.tokenAuthHolder = tokenAuthHolder;
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
        String jwtToken = tokenAuthHolder.generateToken(sysUserDetailsDto.getId(), sysUserDetailsDto.getUsername(), sysUserDetailsDto.getRoles());
        // token 放在请求头
        response.addHeader(tokenAuthHolder.getHeader(), jwtToken);
        return ApiResult.ok(sysUserDetailsDto).path(SecurityConfig.LOGOUT_URL);
    }

}
