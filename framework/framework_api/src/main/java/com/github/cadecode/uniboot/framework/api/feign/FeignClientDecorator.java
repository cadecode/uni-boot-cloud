package com.github.cadecode.uniboot.framework.api.feign;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.EscapeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.github.cadecode.uniboot.common.core.util.JacksonUtil;
import com.github.cadecode.uniboot.framework.api.bean.dto.SysUserDto.SysUserDetailsDto;
import com.github.cadecode.uniboot.framework.api.consts.SecurityConst;
import com.github.cadecode.uniboot.framework.api.util.RequestUtil;
import com.github.cadecode.uniboot.framework.api.util.SecurityUtil;
import feign.Client;
import feign.Request;
import feign.Request.Options;
import feign.RequestTemplate;
import feign.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * FeignClient 装饰器
 *
 * @author Cade Li
 * @since 2023/8/1
 */
public class FeignClientDecorator implements Client {

    private final Client delegate;

    public FeignClientDecorator(Client delegate) {
        this.delegate = delegate;
    }

    @Override
    public Response execute(Request request, Options options) throws IOException {
        configRequestTemplate(request.requestTemplate());
        Response response = delegate.execute(request, options);
        getResponseHeader(response.headers());
        return response;
    }

    protected void configRequestTemplate(RequestTemplate requestTemplate) {
        HttpServletRequest servletRequest = RequestUtil.getRequest();
        if (ObjectUtil.isNull(servletRequest)) {
            return;
        }
        // 传递用户 token
        String token = SecurityUtil.getTokenFromRequest(servletRequest);
        if (StrUtil.isNotEmpty(token)) {
            requestTemplate.header(SecurityConst.HEAD_TOKEN, token);
        }
        // 传递用户详细信息
        String userDetailsJson = ServletUtil.getHeader(servletRequest, SecurityConst.HEAD_USER_DETAILS, CharsetUtil.CHARSET_UTF_8);
        if (StrUtil.isEmpty(userDetailsJson)) {
            SysUserDetailsDto userDetailsDto = SecurityUtil.getUserDetails(null);
            userDetailsJson = JacksonUtil.toJson(userDetailsDto);
        }
        requestTemplate.header(SecurityConst.HEAD_USER_DETAILS, EscapeUtil.escape(userDetailsJson));
        // 配置客户端 IP
        requestTemplate.header("X-Forwarded-For", ServletUtil.getClientIP(servletRequest));
    }

    protected void getResponseHeader(Map<String, Collection<String>> headers) {
        HttpServletResponse servletResponse = RequestUtil.getResponse();
        if (ObjectUtil.isNull(servletResponse)) {
            return;
        }
        // 获取 token
        if (headers.containsKey(SecurityConst.HEAD_TOKEN)) {
            String token = headers.get(SecurityConst.HEAD_TOKEN).stream().findFirst().get();
            servletResponse.addHeader(SecurityConst.HEAD_TOKEN, token);
        }
    }
}
