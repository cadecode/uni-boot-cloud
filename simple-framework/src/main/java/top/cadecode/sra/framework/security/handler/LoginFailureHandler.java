package top.cadecode.sra.framework.security.handler;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import top.cadecode.sra.common.enums.error.AuthErrorEnum;
import top.cadecode.sra.common.exception.ApiException;
import top.cadecode.sra.common.response.ApiResult;
import top.cadecode.sra.common.response.ApiStatus;
import top.cadecode.sra.common.util.JacksonUtil;
import top.cadecode.sra.framework.config.core.SecurityConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Cade Li
 * @date 2021/12/11
 * @description 登录失败处理器
 */
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) {
        ApiResult<Object> result = ApiResult.of(ApiStatus.OK, AuthErrorEnum.TOKEN_CREATE_ERROR, null)
                .path(SecurityConfig.LOGIN_URL);
        Throwable cause = exception.getCause() == null ? exception : exception.getCause();
        if (cause instanceof ApiException) {
            result.moreInfo(((ApiException) cause).getMoreInfo());
        } else if (cause instanceof BadCredentialsException) {
            result.moreInfo("密码错误");
        }
        ServletUtil.write(response, JacksonUtil.toJson(result), ContentType.JSON.toString(CharsetUtil.CHARSET_UTF_8));
    }
}
