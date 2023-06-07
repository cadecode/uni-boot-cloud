package top.cadecode.uniboot.framework.security.handler;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import top.cadecode.uniboot.common.core.enums.error.AuthErrorEnum;
import top.cadecode.uniboot.common.core.response.ApiResult;
import top.cadecode.uniboot.common.core.util.JacksonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 未认证处理器
 *
 * @author Cade Li
 * @date 2021/12/11
 */
@Component
public class NoAuthenticationHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) {
        ApiResult<Object> result = ApiResult.error(AuthErrorEnum.TOKEN_NOT_EXIST).path(request.getRequestURI());
        response.setStatus(AuthErrorEnum.TOKEN_NOT_EXIST.getStatus());
        ServletUtil.write(response, JacksonUtil.toJson(result), ContentType.JSON.toString(CharsetUtil.CHARSET_UTF_8));
    }
}
