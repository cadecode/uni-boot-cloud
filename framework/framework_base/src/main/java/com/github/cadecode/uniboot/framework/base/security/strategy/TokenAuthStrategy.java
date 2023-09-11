package com.github.cadecode.uniboot.framework.base.security.strategy;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import com.github.cadecode.uniboot.common.core.enums.ApiErrorCode;
import com.github.cadecode.uniboot.common.core.extension.strategy.StrategyService;
import com.github.cadecode.uniboot.common.core.util.JacksonUtil;
import com.github.cadecode.uniboot.common.core.web.response.ApiResult;
import com.github.cadecode.uniboot.framework.base.security.model.SysUserDetails;
import com.github.cadecode.uniboot.framework.base.util.RequestUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Token 校验过滤服务
 *
 * @author Cade Li
 * @since 2023/6/25
 */
public abstract class TokenAuthStrategy implements StrategyService {

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
    protected void writeResponse(HttpServletResponse response, ApiErrorCode errorCode, String requestURI) {
        ApiResult<Object> result = ApiResult.error(errorCode).path(requestURI);
        response.setStatus(errorCode.getStatus());
        ServletUtil.write(response, JacksonUtil.toJson(result), ContentType.JSON.toString(CharsetUtil.CHARSET_UTF_8));
    }

    /**
     * 模板方法
     * 由 handler 方法提供处理
     */
    public void filter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (RequestUtil.isInnerRequest(request)) {
            SysUserDetails userDetailsDto = RequestUtil.getInnerUserDetails(request);
            if (ObjUtil.isNotNull(userDetailsDto)) {
                setAuthentication(request, userDetailsDto);
                filterChain.doFilter(request, response);
                return;
            }
        }
        handler(request, response, filterChain);
    }

    public abstract void handler(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException;

}
