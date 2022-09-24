package top.cadecode.uniboot.framework.security.handler;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import top.cadecode.uniboot.common.consts.CacheKeyPrefix;
import top.cadecode.uniboot.common.datasource.CacheKeyGenerator;
import top.cadecode.uniboot.common.enums.AuthModelEnum;
import top.cadecode.uniboot.common.response.ApiResult;
import top.cadecode.uniboot.common.util.JacksonUtil;
import top.cadecode.uniboot.common.util.RedisUtil;
import top.cadecode.uniboot.framework.config.SecurityConfig;
import top.cadecode.uniboot.framework.security.LoginSuccessHandler;
import top.cadecode.uniboot.framework.security.TokenAuthHolder;
import top.cadecode.uniboot.system.bean.dto.SysUserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * 登录成功处理器
 *
 * @author Cade Li
 * @date 2021/12/11
 */
@RequiredArgsConstructor
@Component
@ConditionalOnProperty(name = "uni-boot.security.auth-model", havingValue = "redis")
public class RedisLoginSuccessHandler extends LoginSuccessHandler {

    /**
     * Token 处理器
     */
    private final TokenAuthHolder tokenAuthHolder;

    @Override
    public AuthModelEnum getAuthModel() {
        return AuthModelEnum.REDIS;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        // 从认证信息中获取用户对象
        SysUserDto sysUserDto = (SysUserDto) authentication.getPrincipal();
        // 生成 uuid token
        String uuidToken = tokenAuthHolder.generateUUID();
        // token 放在请求头
        response.addHeader(tokenAuthHolder.getHeader(), uuidToken);
        // 生成存放登录信息的 redis key
        String loginUserKey = CacheKeyGenerator.key(CacheKeyPrefix.LOGIN_USER, uuidToken);
        RedisUtil.set(loginUserKey, sysUserDto, tokenAuthHolder.getExpiration(), TimeUnit.SECONDS);
        // 写入响应
        ApiResult<SysUserDto> result = ApiResult.ok(sysUserDto).path(SecurityConfig.LOGOUT_URL);
        ServletUtil.write(response, JacksonUtil.toJson(result), ContentType.JSON.toString(CharsetUtil.CHARSET_UTF_8));
    }
}
