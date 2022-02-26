package top.cadecode.framework.security.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import top.cadecode.common.core.response.Result;
import top.cadecode.common.core.response.ResultCode;
import top.cadecode.common.util.JsonUtil;
import top.cadecode.common.util.TokenUtil;
import top.cadecode.common.util.WebUtil;
import top.cadecode.framework.config.SecurityConfig;
import top.cadecode.framework.model.entity.SecurityUser;
import top.cadecode.framework.model.mapper.SecurityUserMapper;
import top.cadecode.framework.model.vo.SecurityUserVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Cade Li
 * @date 2021/12/11
 * @description 登录成功处理器
 */
@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenUtil tokenUtil;
    private final SecurityUserMapper securityUserMapper;

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
        securityUserVo.setToken(tokenUtil.generateToken(id, username, roles));
        // 为用户 VO 设置刷新 Token
        String refreshToken = UUID.randomUUID().toString();
        securityUserVo.setRefreshToken(refreshToken);
        // 更新属性 Token
        securityUserMapper.updateSecurityUserToken(id, refreshToken);
        // 创建成功的返回内容
        Result<SecurityUserVo> result = Result.of(ResultCode.SUCCESS, securityUserVo)
                .path(SecurityConfig.LOGIN_URL);
        // 写入响应
        WebUtil.writeJsonToResponse(response, JsonUtil.objToStr(result));
    }
}
