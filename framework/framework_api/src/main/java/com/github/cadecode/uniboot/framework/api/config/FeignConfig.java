package com.github.cadecode.uniboot.framework.api.config;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.EscapeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cadecode.uniboot.common.core.util.JacksonUtil;
import com.github.cadecode.uniboot.framework.api.bean.dto.SysUserDto.SysUserDetailsDto;
import com.github.cadecode.uniboot.framework.api.consts.SecurityConst;
import com.github.cadecode.uniboot.framework.api.feign.FeignClientDecorator;
import com.github.cadecode.uniboot.framework.api.feign.FeignErrorDecoder;
import com.github.cadecode.uniboot.framework.api.util.RequestUtil;
import com.github.cadecode.uniboot.framework.api.util.SecurityUtil;
import feign.Client;
import feign.Client.Default;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.ErrorDecoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.loadbalancer.LoadBalancedRetryFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerProperties;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.cloud.openfeign.loadbalancer.FeignBlockingLoadBalancerClient;
import org.springframework.cloud.openfeign.loadbalancer.RetryableFeignBlockingLoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

/**
 * OpenFeign 配置类
 *
 * @author Cade Li
 * @since 2023/7/29
 */
@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return this::configRequestTemplate;
    }

    protected void configRequestTemplate(RequestTemplate requestTemplate) {
        // 设置内部请求来源标识
        requestTemplate.header(SecurityConst.HEAD_SOURCE, SecurityConst.HEAD_SOURCE_VALUE);
        HttpServletRequest servletRequest = RequestUtil.getRequest();
        if (ObjectUtil.isNull(servletRequest)) {
            return;
        }
        // 配置客户端 IP
        requestTemplate.header(SecurityConst.HEAD_FORWARDED_FOR, ServletUtil.getClientIP(servletRequest));
        // 传递 trace id
        String traceId = ServletUtil.getHeader(servletRequest, SecurityConst.HEAD_TRACE_ID, CharsetUtil.CHARSET_UTF_8);
        if (ObjectUtil.isNotEmpty(traceId)) {
            requestTemplate.header(SecurityConst.HEAD_TRACE_ID, traceId);
        }
        // 传递用户 token
        String token = SecurityUtil.getTokenFromRequest(servletRequest);
        if (StrUtil.isNotEmpty(token)) {
            requestTemplate.header(SecurityConst.HEAD_TOKEN, token);
        }
        // 传递用户信息
        String userDetailsJson = ServletUtil.getHeader(servletRequest, SecurityConst.HEAD_USER_DETAILS, CharsetUtil.CHARSET_UTF_8);
        // 不存在则生成
        if (StrUtil.isEmpty(userDetailsJson)) {
            SysUserDetailsDto userDetailsDto = SecurityUtil.getUserDetails(null);
            userDetailsJson = JacksonUtil.toJson(userDetailsDto);
        }
        requestTemplate.header(SecurityConst.HEAD_USER_DETAILS, EscapeUtil.escape(userDetailsJson));
    }

    // 覆盖 feign 配置
    // org.springframework.cloud.openfeign.loadbalancer.DefaultFeignLoadBalancerConfiguration

    @Bean
    @ConditionalOnProperty(value = "spring.cloud.loadbalancer.retry.enabled", havingValue = "false")
    public Client feignClient(LoadBalancerClient loadBalancerClient, LoadBalancerProperties properties,
                              LoadBalancerClientFactory loadBalancerClientFactory) {
        FeignClientDecorator decorator = new FeignClientDecorator(new Default(null, null));
        return new FeignBlockingLoadBalancerClient(decorator, loadBalancerClient, properties, loadBalancerClientFactory);
    }

    @Bean
    @ConditionalOnProperty(value = "spring.cloud.loadbalancer.retry.enabled", havingValue = "true", matchIfMissing = true)
    public Client feignRetryClient(LoadBalancerClient loadBalancerClient, LoadBalancedRetryFactory loadBalancedRetryFactory,
                                   LoadBalancerProperties properties, LoadBalancerClientFactory loadBalancerClientFactory) {
        FeignClientDecorator decorator = new FeignClientDecorator(new Default(null, null));
        return new RetryableFeignBlockingLoadBalancerClient(decorator, loadBalancerClient, loadBalancedRetryFactory, properties, loadBalancerClientFactory);
    }

    @Bean
    public ErrorDecoder errorDecoder(ObjectMapper objectMapper) {
        return new FeignErrorDecoder(objectMapper);
    }
}
