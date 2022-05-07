package top.cadecode.sra.framework.security.handler;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import cn.hutool.json.JSONUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import top.cadecode.sra.common.core.response.Result;
import top.cadecode.sra.common.enums.BaseErrorEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Cade Li
 * @date 2021/12/11
 * @description 权限不足处理器
 */
@Component
public class NoAuthorityHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) {
        Result<Object> result = Result.of(BaseErrorEnum.TOKEN_NO_AUTHORITY)
                .path(request.getRequestURI());
        ServletUtil.write(response, JSONUtil.toJsonStr(result), ContentType.JSON.toString(CharsetUtil.CHARSET_UTF_8));
    }
}
