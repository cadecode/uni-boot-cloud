package top.cadecode.framework.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import top.cadecode.common.core.response.CommonResponse;
import top.cadecode.common.enums.AuthErrorEnum;
import top.cadecode.common.util.JsonUtil;
import top.cadecode.common.util.WebUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Cade Li
 * @date 2021/12/11
 * @description 未认证处理器
 */
@Component
public class NoAuthenticationHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) {
        CommonResponse<Object> commonResponse = CommonResponse.of(AuthErrorEnum.TOKEN_NOT_EXIST)
                .path(request.getRequestURI());
        WebUtil.writeJsonToResponse(response, JsonUtil.objToStr(commonResponse));
    }
}
