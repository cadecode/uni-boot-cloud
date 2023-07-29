package com.github.cadecode.uniboot.framework.api.config;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.EscapeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.github.cadecode.uniboot.common.core.util.JacksonUtil;
import com.github.cadecode.uniboot.framework.api.bean.dto.SysUserDto.SysUserDetailsDto;
import com.github.cadecode.uniboot.framework.api.consts.SecurityConst;
import com.github.cadecode.uniboot.framework.api.util.SecurityUtil;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
        return requestTemplate -> {
            // 获取请求对象
            HttpServletRequest sRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            if (ObjectUtil.isNull(sRequest)) {
                return;
            }
            // 传递用户 token
            String token = SecurityUtil.getTokenFromRequest(sRequest);
            if (StrUtil.isNotEmpty(token)) {
                requestTemplate.header(SecurityUtil.getHeader(), token);
            }
            // 传递用户详细信息
            String userDetailsJson = ServletUtil.getHeader(sRequest, SecurityConst.USER_DETAILS, CharsetUtil.CHARSET_UTF_8);
            if (StrUtil.isEmpty(userDetailsJson)) {
                SysUserDetailsDto userDetailsDto = SecurityUtil.getUserDetails(null);
                userDetailsJson = JacksonUtil.toJson(userDetailsDto);
            }
            requestTemplate.header(SecurityConst.USER_DETAILS, EscapeUtil.escape(userDetailsJson));
            // 配置客户端 IP
            requestTemplate.header("X-Forwarded-For", ServletUtil.getClientIP(sRequest));
        };
    }
}
