package com.github.cadecode.uniboot.common.plugin.mybatis.aspect;

import cn.hutool.core.util.ObjUtil;
import com.github.cadecode.uniboot.common.core.util.SpringUtil;
import com.github.cadecode.uniboot.common.plugin.mybatis.annotation.UseDataScope;
import com.github.cadecode.uniboot.common.plugin.mybatis.handler.DataScopeResolver;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 数据权限注解切面
 *
 * @author Cade Li
 * @since 2024/5/23
 */
@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class DataScopeAspect {

    private static final ThreadLocal<DataScopeParam> DATA_SCOPE_LOCAL = new ThreadLocal<>();

    public static DataScopeParam currDataScope() {
        return DATA_SCOPE_LOCAL.get();
    }

    @Pointcut("@within(com.github.cadecode.uniboot.common.plugin.mybatis.annotation.UseDataScope) " +
            "|| @annotation(com.github.cadecode.uniboot.common.plugin.mybatis.annotation.UseDataScope)")
    public void pointCut() {

    }

    @Before("pointCut()")
    public void before(JoinPoint point) {
        DataScopeParam dataScopeParam = getDataScopeParam(point);
        DATA_SCOPE_LOCAL.set(dataScopeParam);
    }

    @After("pointCut()")
    public void after(JoinPoint point) {
        DATA_SCOPE_LOCAL.remove();
    }

    /**
     * 获取数据权限注解内容
     */
    private DataScopeParam getDataScopeParam(JoinPoint point) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        // 获取方法上的注解内容
        UseDataScope useDataScopeM = methodSignature.getMethod().getAnnotation(UseDataScope.class);
        // 获取类上的注解内容
        UseDataScope useDataScopeC = methodSignature.getMethod().getDeclaringClass().getAnnotation(UseDataScope.class);
        DataScopeParam dataScopeParam = new DataScopeParam();
        if (Objects.isNull(useDataScopeM) && Objects.isNull(useDataScopeC)) {
            return dataScopeParam;
        }
        // 方法上有注解
        if (Objects.nonNull(useDataScopeM)) {
            dataScopeParam.setEnableFilter(useDataScopeM.enableFilter());
            // 方法上有注解，且类上也有
            if (Objects.nonNull(useDataScopeC)) {
                dataScopeParam.setField(ObjUtil.defaultIfEmpty(useDataScopeM.field(),
                        ObjUtil.defaultIfEmpty(useDataScopeC.field(), UseDataScope.DEFAULT_FIELD)));
                dataScopeParam.setResolverName(ObjUtil.defaultIfEmpty(useDataScopeM.resolverName(),
                        ObjUtil.defaultIfEmpty(useDataScopeC.field(), UseDataScope.DEFAULT_RESOLVER_NAME)));
            } else {
                // 方法上有注解，类上无注解
                dataScopeParam.setField(ObjUtil.defaultIfEmpty(useDataScopeM.field(), UseDataScope.DEFAULT_FIELD));
                dataScopeParam.setResolverName(ObjUtil.defaultIfEmpty(useDataScopeM.resolverName(), UseDataScope.DEFAULT_RESOLVER_NAME));
            }
        } else {
            // 方法上无注解，类上有注解
            dataScopeParam.setEnableFilter(useDataScopeC.enableFilter());
            dataScopeParam.setField(ObjUtil.defaultIfEmpty(useDataScopeC.field(), UseDataScope.DEFAULT_FIELD));
            dataScopeParam.setResolverName(ObjUtil.defaultIfEmpty(useDataScopeC.resolverName(), UseDataScope.DEFAULT_RESOLVER_NAME));
        }
        // 从容器取出数据范围解析器
        DataScopeResolver scopeResolver = SpringUtil.getBean(dataScopeParam.getResolverName(), DataScopeResolver.class);
        dataScopeParam.setDataScopeResolver(scopeResolver);
        return dataScopeParam;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DataScopeParam {

        private boolean enableFilter;
        private String field;
        private String resolverName;
        private DataScopeResolver dataScopeResolver;

    }

}
