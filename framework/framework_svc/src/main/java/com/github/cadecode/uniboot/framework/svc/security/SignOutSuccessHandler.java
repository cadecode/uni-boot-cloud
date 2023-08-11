package com.github.cadecode.uniboot.framework.svc.security;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import com.github.cadecode.uniboot.common.core.util.JacksonUtil;
import com.github.cadecode.uniboot.common.core.web.response.ApiResult;
import com.github.cadecode.uniboot.common.plugin.cache.util.KeyGeneUtil;
import com.github.cadecode.uniboot.common.plugin.cache.util.RedisUtil;
import com.github.cadecode.uniboot.framework.api.consts.KeyPrefix;
import com.github.cadecode.uniboot.framework.api.consts.SecurityConst;
import com.github.cadecode.uniboot.framework.api.enums.AuthErrorEnum;
import com.github.cadecode.uniboot.framework.api.enums.AuthModelEnum;
import com.github.cadecode.uniboot.framework.base.config.SecurityConfig.SecurityProperties;
import com.github.cadecode.uniboot.framework.svc.config.FrameSecurityConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

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

    private final SecurityProperties properties;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) {
        // 删除保存登录信息的 redis key
        String uuidToken = request.getHeader(SecurityConst.HEAD_TOKEN);
        if (StrUtil.isNotEmpty(uuidToken) && properties.getAuthModel() == AuthModelEnum.REDIS) {
            String loginUserKey = KeyGeneUtil.key(KeyPrefix.LOGIN_USER, uuidToken);
            RedisUtil.del(loginUserKey);
        }
        // 写入响应
        ApiResult<Object> result = ApiResult.error(AuthErrorEnum.TOKEN_LOGOUT).path(FrameSecurityConfig.LOGOUT_URL);
        response.setStatus(AuthErrorEnum.TOKEN_LOGOUT.getStatus());
        ServletUtil.write(response, JacksonUtil.toJson(result), ContentType.JSON.toString(CharsetUtil.CHARSET_UTF_8));
    }
}
