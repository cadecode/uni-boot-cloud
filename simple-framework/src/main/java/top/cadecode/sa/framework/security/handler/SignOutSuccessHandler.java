package top.cadecode.sa.framework.security.handler;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import cn.hutool.json.JSONUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import top.cadecode.sa.common.core.response.Result;
import top.cadecode.sa.common.core.response.ResultCode;
import top.cadecode.sa.framework.config.SecurityConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Cade Li
 * @date 2021/12/11
 * @description 注销成功处理器
 */
@Component
public class SignOutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) {
        Result<Object> result = Result.of(ResultCode.SUCCESS)
                .path(SecurityConfig.LOGOUT_URL);
        ServletUtil.write(response, JSONUtil.toJsonStr(result), ContentType.JSON.toString(CharsetUtil.CHARSET_UTF_8));
    }
}
