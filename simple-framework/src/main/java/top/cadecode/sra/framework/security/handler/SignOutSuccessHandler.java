package top.cadecode.sra.framework.security.handler;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import top.cadecode.sra.common.consts.CacheKeyPrefix;
import top.cadecode.sra.common.datasource.CacheKeyGenerator;
import top.cadecode.sra.common.response.ApiResult;
import top.cadecode.sra.common.util.JacksonUtil;
import top.cadecode.sra.common.util.RedisUtil;
import top.cadecode.sra.framework.config.core.SecurityConfig;
import top.cadecode.sra.framework.security.TokenAuthHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Cade Li
 * @date 2021/12/11
 * @description 注销成功处理器
 */
@RequiredArgsConstructor
@Component
public class SignOutSuccessHandler implements LogoutSuccessHandler {

    private final TokenAuthHolder tokenAuthHolder;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) {
        // 删除保存登录信息的 redis key
        String uuidToken = request.getHeader(tokenAuthHolder.getHeader());
        if (StrUtil.isNotEmpty(uuidToken)) {
            String loginUserKey = CacheKeyGenerator.key(CacheKeyPrefix.LOGIN_USER, uuidToken);
            RedisUtil.del(loginUserKey);
        }
        // 写入响应
        ApiResult<Object> result = ApiResult.ok(null).path(SecurityConfig.LOGOUT_URL);
        ServletUtil.write(response, JacksonUtil.toJson(result), ContentType.JSON.toString(CharsetUtil.CHARSET_UTF_8));
    }
}
