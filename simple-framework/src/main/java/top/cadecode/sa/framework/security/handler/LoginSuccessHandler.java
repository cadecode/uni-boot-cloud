package top.cadecode.sa.framework.security.handler;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import top.cadecode.sa.common.core.response.Result;
import top.cadecode.sa.common.core.response.ResultCode;
import top.cadecode.sa.framework.config.SecurityConfig;
import top.cadecode.sa.framework.model.entity.SecurityUser;
import top.cadecode.sa.framework.model.service.SecurityUserSrvice;
import top.cadecode.sa.framework.model.vo.SecurityUserVo;
import top.cadecode.sa.framework.security.JwtTokenHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Cade Li
 * @date 2021/12/11
 * @description 登录成功处理器
 */
@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenHolder jwtTokenHolder;
    private final StringRedisTemplate redisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        // 从认证信息中获取用户对象
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        // 创建用户 VO，并设置属性
        SecurityUserVo securityUserVo = new SecurityUserVo();
        BeanUtils.copyProperties(securityUser, securityUserVo);
        // 获取 ID，用户名和角色
        Long id = securityUser.getId();
        String username = securityUser.getUsername();
        List<String> roles = securityUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        // 为用户 VO 设置角色
        securityUserVo.setRoles(roles);
        // 为用户 VO 设置 JWT Token
        securityUserVo.setToken(jwtTokenHolder.generateToken(id, username, roles));
        // 为用户 VO 设置 refresh Token
        String refreshToken = UUID.randomUUID().toString();
        securityUserVo.setRefreshToken(refreshToken);
        // 存储 refresh token 到 redis
        String key = SecurityUserSrvice.USER_REFRESH_TOKEN_PREFIX + securityUser.getId();
        redisTemplate.opsForValue().set(key, refreshToken, jwtTokenHolder.getRefreshExpiration(), TimeUnit.SECONDS);
        // 创建成功的返回内容
        Result<SecurityUserVo> result = Result.of(ResultCode.SUCCESS, securityUserVo)
                .path(SecurityConfig.LOGIN_URL);
        // 写入响应
        ServletUtil.write(response, JSONUtil.toJsonStr(result), ContentType.JSON.toString(CharsetUtil.CHARSET_UTF_8));
    }
}
