package com.github.cadecode.uniboot.framework.api.util;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.EscapeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.github.cadecode.uniboot.common.core.util.JacksonUtil;
import com.github.cadecode.uniboot.framework.api.consts.SecurityConst;
import com.github.cadecode.uniboot.framework.api.security.model.SysUserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * spring web 工具类
 *
 * @author Cade Li
 * @since 2023/8/1
 */
public class RequestUtil {

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (ObjectUtil.isNull(requestAttributes)) {
            return null;
        }
        return requestAttributes.getRequest();
    }

    public static HttpServletResponse getResponse() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (ObjectUtil.isNull(requestAttributes)) {
            return null;
        }
        return requestAttributes.getResponse();
    }

    /**
     * 根据请求头判断是否是内部请求
     *
     * @param request 请求对象，为 null 时自动从上下文获取
     * @return 是否是内部请求
     */
    public static boolean isInnerRequest(HttpServletRequest request) {
        if (ObjectUtil.isNull(request)) {
            request = getRequest();
        }
        if (ObjectUtil.isNull(request)) {
            return false;
        }
        String source = ServletUtil.getHeader(request, SecurityConst.HEAD_SOURCE, CharsetUtil.CHARSET_UTF_8);
        return ObjectUtil.equal(source, SecurityConst.HEAD_SOURCE_VALUE);
    }

    /**
     * 根据请求头获取内部请求携带的用户信息
     *
     * @param request 请求对象，为 null 时自动从上下文获取
     * @return 内部请求携带的用户信息
     */
    public static SysUserDetails getInnerUserDetails(HttpServletRequest request) {
        if (ObjectUtil.isNull(request)) {
            request = getRequest();
        }
        if (ObjectUtil.isNull(request)) {
            return null;
        }
        // feign 拦截器填充时做了转义处理
        String escapedUserDetailsJson = ServletUtil.getHeader(request, SecurityConst.HEAD_USER_DETAILS, CharsetUtil.CHARSET_UTF_8);
        if (ObjectUtil.isNotEmpty(escapedUserDetailsJson)) {
            return JacksonUtil.toBean(EscapeUtil.unescape(escapedUserDetailsJson), SysUserDetails.class);
        }
        return null;
    }
}
