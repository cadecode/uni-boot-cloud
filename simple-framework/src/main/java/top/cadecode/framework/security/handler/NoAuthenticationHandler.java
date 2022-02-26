package top.cadecode.framework.security.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import top.cadecode.common.core.response.Result;
import top.cadecode.common.enums.BaseErrorEnum;
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
        Result<Object> result = Result.of(BaseErrorEnum.TOKEN_NOT_EXIST)
                .path(request.getRequestURI());
        WebUtil.writeJsonToResponse(response, JsonUtil.objToStr(result));
    }
}
