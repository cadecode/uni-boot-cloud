package top.cadecode.framework.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import top.cadecode.common.core.exception.CommonException;
import top.cadecode.common.core.response.CommonResponse;
import top.cadecode.common.enums.AuthErrorEnum;
import top.cadecode.common.util.JsonUtil;
import top.cadecode.common.util.WebUtil;
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
        CommonResponse<Object> commonResponse = CommonResponse.of(AuthErrorEnum.TOKEN_CREATE_ERROR)
                .path(SecurityConfig.LOGIN_URL);
        Throwable cause = exception.getCause() == null ? exception : exception.getCause();
        if (cause instanceof CommonException) {
            commonResponse.errorMsg((((CommonException) cause).getErrorMsg()));
        } else if (cause instanceof BadCredentialsException) {
            commonResponse.errorMsg("用户名或密码错误");
        }
        WebUtil.writeJsonToResponse(response, JsonUtil.objToStr(commonResponse));
    }
}
