package com.github.cadecode.uniboot.common.plugin.datasource.aspect;

import com.github.cadecode.uniboot.common.plugin.datasource.annotation.DynamicDs;
import com.github.cadecode.uniboot.common.plugin.datasource.dynamic.DynamicDsHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 动态数据源切换 AOP 类
 *
 * @author Cade Li
 * @date 2021/12/3
 */
@Slf4j
@Aspect
@Component
@Order(-1)
public class DynamicDsAspect {

    @Pointcut("@within(com.github.cadecode.uniboot.common.plugin.datasource.annotation.DynamicDs) " +
            "|| @annotation(com.github.cadecode.uniboot.common.plugin.datasource.annotation.DynamicDs)")
    public void pointCut() {

    }

    /**
     * 切换数据源
     */
    @Before("pointCut()")
    public void switchDataSource(JoinPoint point) {
        // 获取方法上的注解
        String dataSourceKey = getDataSourceKey(point);
        // 设置数据源
        if (!DynamicDsHolder.containDataSourceKey(dataSourceKey)) {
            log.info("Switch datasource fail，{} not found", dataSourceKey);
            return;
        }
        DynamicDsHolder.setDataSourceKey(dataSourceKey);
        String methodName = point.getSignature().getDeclaringTypeName() + '.' + point.getSignature().getName();
        log.info("Switch datasource to {}，execute method [{}]", dataSourceKey, methodName);
    }

    /**
     * 重置数据源
     */
    @After("pointCut()")
    public void resetDataSource(JoinPoint point) {
        // 将数据源恢复为之前的数据源
        DynamicDsHolder.clearDataSourceKey();
        log.info("Reset datasource to {}", DynamicDsHolder.getDataSourceKey());
    }

    /**
     * 获取 DynamicDs 注解内容，以方法为主
     */
    private String getDataSourceKey(JoinPoint point) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        DynamicDs dsM = methodSignature.getMethod().getAnnotation(DynamicDs.class);
        // 获取数据源 key
        String dataSourceKey;
        if (dsM != null) {
            return dsM.value();
        }
        // 获取取类上的注解内容
        dataSourceKey = methodSignature.getMethod()
                .getDeclaringClass()
                .getAnnotation(DynamicDs.class)
                .value();
        return dataSourceKey;
    }
}
