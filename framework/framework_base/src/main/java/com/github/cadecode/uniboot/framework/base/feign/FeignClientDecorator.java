package com.github.cadecode.uniboot.framework.base.feign;

import cn.hutool.core.util.ObjectUtil;
import com.github.cadecode.uniboot.framework.api.consts.HttpConst;
import com.github.cadecode.uniboot.framework.base.util.RequestUtil;
import feign.Client;
import feign.Request;
import feign.Request.Options;
import feign.Response;

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
        Response response = delegate.execute(request, options);
        fillResponseHeader(response.headers());
        return response;
    }

    /**
     * 回填响应头到请求上下文的 HttpServletResponse 中
     */
    protected void fillResponseHeader(Map<String, Collection<String>> headers) {
        HttpServletResponse servletResponse = RequestUtil.getResponse();
        if (ObjectUtil.isNull(servletResponse)) {
            return;
        }
        // 获取 token
        if (headers.containsKey(HttpConst.HEAD_TOKEN)) {
            String token = headers.get(HttpConst.HEAD_TOKEN).stream().findFirst().get();
            servletResponse.addHeader(HttpConst.HEAD_TOKEN, token);
        }
    }
}
