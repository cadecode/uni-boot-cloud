package com.github.cadecode.uniboot.framework.base.security.filter;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.github.cadecode.uniboot.framework.api.consts.HttpConst;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter, trace id to MDC
 *
 * @author Cade Li
 * @since 2023/9/11
 */
public class TraceInfoFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain
            filterChain)
            throws ServletException, IOException {
        String traceId = ServletUtil.getHeader(request, HttpConst.HEAD_TRACE_ID, CharsetUtil.CHARSET_UTF_8);
        if (ObjUtil.isNotEmpty(traceId)) {
            MDC.put(HttpConst.HEAD_TRACE_ID, traceId);
        }
        filterChain.doFilter(request, response);
    }
}
