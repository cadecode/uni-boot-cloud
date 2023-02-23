package top.cadecode.uniboot.framework.security.handler;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import top.cadecode.uniboot.common.enums.error.AuthErrorEnum;
import top.cadecode.uniboot.common.response.ApiResult;
import top.cadecode.uniboot.common.util.JacksonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限不足处理器
 *
 * @author Cade Li
 * @date 2021/12/11
 */
@Component
public class NoAuthorityHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) {
        ApiResult<Object> result = ApiResult.error(AuthErrorEnum.TOKEN_NO_AUTHORITY).path(request.getRequestURI());
        ServletUtil.write(response, JacksonUtil.toJson(result), ContentType.JSON.toString(CharsetUtil.CHARSET_UTF_8));
    }
}
