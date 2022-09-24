package top.cadecode.uniboot.framework.security;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import top.cadecode.uniboot.common.enums.AuthModelEnum;
import top.cadecode.uniboot.common.exception.UniErrorCode;
import top.cadecode.uniboot.common.response.ApiResult;
import top.cadecode.uniboot.common.response.ApiStatus;
import top.cadecode.uniboot.common.util.JacksonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Token 校验过滤器抽象，继承 OncePerRequestFilter
 *
 * @author Cade Li
 * @date 2022/5/28
 */
public abstract class TokenAuthFilter extends OncePerRequestFilter {

    /**
     * 返回当前模式，用于策略模式
     */
    public abstract AuthModelEnum getAuthModel();

    /**
     * 设置认证信息
     *
     * @param request     请求对象
     * @param userDetails 用户信息
     */
    protected void setAuthentication(HttpServletRequest request, UserDetails userDetails) {
        // 构造 UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        // 设置认证信息，用于后面的过滤器使用
        authentication.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * 将错误信息写入响应
     *
     * @param response   响应对象
     * @param errorCode  错误信息枚举类
     * @param requestURI 请求路径
     */
    protected void writeResponse(HttpServletResponse response, UniErrorCode errorCode, String requestURI) {
        ApiResult<Object> result = ApiResult.error(errorCode).status(ApiStatus.NO_AUTHENTICATION).path(requestURI);
        ServletUtil.write(response, JacksonUtil.toJson(result), ContentType.JSON.toString(CharsetUtil.CHARSET_UTF_8));
    }
}
