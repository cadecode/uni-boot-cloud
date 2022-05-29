package top.cadecode.sra.framework.security.handler;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import top.cadecode.sra.common.enums.error.AuthErrorEnum;
import top.cadecode.sra.common.response.ApiResult;
import top.cadecode.sra.common.response.ApiStatus;
import top.cadecode.sra.common.util.JacksonUtil;

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
        ApiResult<Object> result = ApiResult.of(ApiStatus.NO_AUTHENTICATION, AuthErrorEnum.TOKEN_NOT_EXIST, null)
                .path(request.getRequestURI());
        ServletUtil.write(response, JacksonUtil.toJson(result), ContentType.JSON.toString(CharsetUtil.CHARSET_UTF_8));
    }
}
