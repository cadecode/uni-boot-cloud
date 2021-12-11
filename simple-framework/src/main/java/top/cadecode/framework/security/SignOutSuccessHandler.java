package top.cadecode.framework.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import top.cadecode.common.core.response.CommonResponse;
import top.cadecode.common.core.response.ResponseCode;
import top.cadecode.common.util.JsonUtil;
import top.cadecode.common.util.WebUtil;
import top.cadecode.framework.config.SecurityConfig;

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
        CommonResponse<Object> commonResponse = CommonResponse.of(ResponseCode.SUCCESS)
                .path(SecurityConfig.LOGOUT_URL);
        WebUtil.writeJsonToResponse(response, JsonUtil.objToStr(commonResponse));
    }
}
