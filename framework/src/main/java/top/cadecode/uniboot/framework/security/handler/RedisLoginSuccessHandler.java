package top.cadecode.uniboot.framework.security.handler;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import top.cadecode.uniboot.common.core.web.response.ApiResult;
import top.cadecode.uniboot.common.plugin.cache.util.CacheKeyGenerator;
import top.cadecode.uniboot.common.plugin.cache.util.RedisUtil;
import top.cadecode.uniboot.framework.bean.dto.SysUserDto;
import top.cadecode.uniboot.framework.bean.dto.SysUserDto.SysUserDetailsDto;
import top.cadecode.uniboot.framework.config.SecurityConfig;
import top.cadecode.uniboot.framework.consts.KeyPrefix;
import top.cadecode.uniboot.framework.enums.AuthModelEnum;
import top.cadecode.uniboot.framework.security.LoginSuccessHandler;
import top.cadecode.uniboot.framework.security.TokenAuthHolder;
import top.cadecode.uniboot.framework.service.SysUserService;

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
@ConditionalOnProperty(name = "uni-boot.security.auth-model", havingValue = "redis")
public class RedisLoginSuccessHandler extends LoginSuccessHandler {

    /**
     * Token 处理器
     */
    private final TokenAuthHolder tokenAuthHolder;

    public RedisLoginSuccessHandler(TokenAuthHolder tokenAuthHolder, SysUserService sysUserService) {
        super(sysUserService);
        this.tokenAuthHolder = tokenAuthHolder;
    }

    @Override
    public AuthModelEnum getAuthModel() {
        return AuthModelEnum.REDIS;
    }

    @Override
    public ApiResult<SysUserDto.SysUserDetailsDto> getResult(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 从认证信息中获取用户对象
        SysUserDetailsDto sysUserDetailsDto = (SysUserDetailsDto) authentication.getPrincipal();
        // 生成 uuid token
        String uuidToken = tokenAuthHolder.generateUUID();
        // token 放在请求头
        response.addHeader(tokenAuthHolder.getHeader(), uuidToken);
        // 生成存放登录信息的 redis key
        String loginUserKey = CacheKeyGenerator.key(KeyPrefix.LOGIN_USER, uuidToken);
        RedisUtil.set(loginUserKey, sysUserDetailsDto, tokenAuthHolder.getExpiration(), TimeUnit.SECONDS);
        return ApiResult.ok(sysUserDetailsDto).path(SecurityConfig.LOGOUT_URL);
    }

}
