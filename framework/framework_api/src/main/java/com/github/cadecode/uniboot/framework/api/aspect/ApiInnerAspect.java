package com.github.cadecode.uniboot.framework.api.aspect;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.github.cadecode.uniboot.common.core.exception.ApiException;
import com.github.cadecode.uniboot.framework.api.annotation.ApiInner;
import com.github.cadecode.uniboot.framework.api.consts.SecurityConst;
import com.github.cadecode.uniboot.framework.api.enums.AuthErrorEnum;
import com.github.cadecode.uniboot.framework.api.util.RequestUtil;
import com.github.cadecode.uniboot.framework.api.util.SecurityUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * ApiInner 注解检查切面
 *
 * @author Cade Li
 * @since 2023/8/2
 */
@Aspect
@Component
public class ApiInnerAspect {

    @Pointcut("@annotation(com.github.cadecode.uniboot.framework.api.annotation.ApiInner)")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void rateLimit(JoinPoint point) {
        // 获取方法上的注解
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        // 获取注解
        ApiInner apiInner = methodSignature.getMethod().getAnnotation(ApiInner.class);
        // 获取 request
        HttpServletRequest servletRequest = RequestUtil.getRequest();
        if (ObjectUtil.isNull(servletRequest)) {
            return;
        }
        // 获取请求头
        String source = ServletUtil.getHeader(servletRequest, SecurityConst.HEAD_SOURCE, CharsetUtil.CHARSET_UTF_8);
        boolean innerFlag = ObjectUtil.equal(source, SecurityConst.HEAD_SOURCE_VALUE);
        // 判断是否仅供内部调用
        if (apiInner.onlyClient() && !innerFlag) {
            throw ApiException.of(AuthErrorEnum.TOKEN_NO_AUTHORITY, "ApiInner AOP：该接口仅供内部调用");
        }
        // 判断是否需要用户信息
        if (apiInner.requireUser() && !SecurityUtil.isAuthenticated(null)) {
            throw ApiException.of(AuthErrorEnum.TOKEN_NOT_EXIST, "ApiInner AOP：该接口要求用户登录");
        }
    }
}
