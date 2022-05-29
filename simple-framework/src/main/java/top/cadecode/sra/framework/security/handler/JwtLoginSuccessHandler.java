package top.cadecode.sra.framework.security.handler;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import top.cadecode.sra.common.enums.AuthModelEnum;
import top.cadecode.sra.common.response.ApiResult;
import top.cadecode.sra.framework.config.core.SecurityConfig;
import top.cadecode.sra.framework.security.LoginSuccessHandler;
import top.cadecode.sra.framework.security.TokenAuthHolder;
import top.cadecode.sra.framework.util.JacksonUtil;
import top.cadecode.sra.system.bean.dto.SysUserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Cade Li
 * @date 2021/12/11
 * @description 登录成功处理器
 */
@RequiredArgsConstructor
@Component
@ConditionalOnProperty(name = "sra.security.auth-model", havingValue = "jwt")
public class JwtLoginSuccessHandler extends LoginSuccessHandler {

    /**
     * Token 处理器
     */
    private final TokenAuthHolder tokenAuthHolder;

    @Override
    public AuthModelEnum getAuthModel() {
        return AuthModelEnum.JWT;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        // 从认证信息中获取用户对象
        SysUserDto sysUserDto = (SysUserDto) authentication.getPrincipal();
        // 生成 jwt token
        String jwtToken = tokenAuthHolder.generateToken(sysUserDto.getId(), sysUserDto.getUsername(), sysUserDto.getRoles());
        // token 放在请求头
        response.addHeader(tokenAuthHolder.getHeader(), jwtToken);
        // 写入响应
        ApiResult<SysUserDto> result = ApiResult.ok(sysUserDto).path(SecurityConfig.LOGOUT_URL);
        ServletUtil.write(response, JacksonUtil.toJson(result), ContentType.JSON.toString(CharsetUtil.CHARSET_UTF_8));
    }
}
