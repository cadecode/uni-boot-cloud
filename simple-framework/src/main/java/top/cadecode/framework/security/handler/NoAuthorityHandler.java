package top.cadecode.framework.security.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
 * @description 权限不足处理器
 */
@Component
public class NoAuthorityHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) {
        Result<Object> result = Result.of(BaseErrorEnum.TOKEN_NO_AUTHORITY)
                .path(request.getRequestURI());
        WebUtil.writeJsonToResponse(response, JsonUtil.objToStr(result));
    }
}
