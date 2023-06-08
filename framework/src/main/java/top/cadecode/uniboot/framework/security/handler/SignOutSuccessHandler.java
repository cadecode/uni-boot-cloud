package top.cadecode.uniboot.framework.security.handler;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import top.cadecode.uniboot.common.core.util.JacksonUtil;
import top.cadecode.uniboot.common.core.web.response.ApiResult;
import top.cadecode.uniboot.common.plugin.cache.util.KeyGeneUtil;
import top.cadecode.uniboot.common.plugin.cache.util.RedisUtil;
import top.cadecode.uniboot.framework.config.SecurityConfig;
import top.cadecode.uniboot.framework.consts.KeyPrefix;
import top.cadecode.uniboot.framework.enums.AuthErrorEnum;
import top.cadecode.uniboot.framework.security.TokenAuthFilter;
import top.cadecode.uniboot.framework.security.filter.RedisTokenAuthFilter;
import top.cadecode.uniboot.framework.util.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 注销成功处理器
 *
 * @author Cade Li
 * @date 2021/12/11
 */
@RequiredArgsConstructor
@Component
public class SignOutSuccessHandler implements LogoutSuccessHandler {

    private final TokenAuthFilter tokenAuthFilter;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) {
        // 删除保存登录信息的 redis key
        String uuidToken = request.getHeader(SecurityUtil.getHeader());
        if (StrUtil.isNotEmpty(uuidToken) && tokenAuthFilter instanceof RedisTokenAuthFilter) {
            String loginUserKey = KeyGeneUtil.key(KeyPrefix.LOGIN_USER, uuidToken);
            RedisUtil.del(loginUserKey);
        }
        // 写入响应
        ApiResult<Object> result = ApiResult.error(AuthErrorEnum.TOKEN_LOGOUT).path(SecurityConfig.LOGOUT_URL);
        response.setStatus(AuthErrorEnum.TOKEN_LOGOUT.getStatus());
        ServletUtil.write(response, JacksonUtil.toJson(result), ContentType.JSON.toString(CharsetUtil.CHARSET_UTF_8));
    }
}
