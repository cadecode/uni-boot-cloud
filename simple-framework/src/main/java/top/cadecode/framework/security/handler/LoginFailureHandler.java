package top.cadecode.framework.security.handler;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import cn.hutool.json.JSONUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import top.cadecode.common.core.exception.BaseException;
import top.cadecode.common.core.response.Result;
import top.cadecode.common.enums.BaseErrorEnum;
import top.cadecode.framework.config.SecurityConfig;

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
        Result<Object> result = Result.of(BaseErrorEnum.TOKEN_CREATE_ERROR)
                .path(SecurityConfig.LOGIN_URL);
        Throwable cause = exception.getCause() == null ? exception : exception.getCause();
        if (cause instanceof BaseException) {
            result.errorMsg((((BaseException) cause).getErrorMsg()));
        } else if (cause instanceof BadCredentialsException) {
            result.errorMsg("用户名或密码错误");
        }
        ServletUtil.write(response, JSONUtil.toJsonStr(result), ContentType.JSON.toString(CharsetUtil.CHARSET_UTF_8));
    }
}
