package top.cadecode.sra.framework.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import top.cadecode.sra.common.annotation.DynamicDS;
import top.cadecode.sra.common.datasource.DynamicDSHolder;

/**
 * @author Cade Li
 * @date 2021/12/3
 * @description 动态数据源切换 AOP 类
 */
@Slf4j
@Aspect
@Component
@Order(-1)
public class DynamicDSAspect {

    @Pointcut("@within(top.cadecode.sra.common.annotation.DynamicDS) " +
            "|| @annotation(top.cadecode.sra.common.annotation.DynamicDS)")
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
        if (!DynamicDSHolder.containDataSourceKey(dataSourceKey)) {
            log.info("切换数据源失败，{} 不存在", dataSourceKey);
            return;
        }
        DynamicDSHolder.setDataSourceKey(dataSourceKey);
        String methodName = point.getSignature().getDeclaringTypeName() + '.' + point.getSignature().getName();
        log.info("切换数据源到 {}，执行方法 [{}]", dataSourceKey, methodName);
    }

    /**
     * 重置数据源
     */
    @After("pointCut()")
    public void resetDataSource(JoinPoint point) {
        // 将数据源恢复为之前的数据源
        DynamicDSHolder.clearDataSourceKey();
        log.info("恢复数据源到 {}", DynamicDSHolder.getDataSourceKey());
    }

    /**
     * 获取 DynamicDS 注解内容，以方法为主
     */
    private String getDataSourceKey(JoinPoint point) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        DynamicDS dsM = methodSignature.getMethod().getAnnotation(DynamicDS.class);
        // 获取数据源 key
        String dataSourceKey;
        if (dsM != null) {
            return dsM.value();
        }
        // 获取取类上的注解内容
        dataSourceKey = methodSignature.getMethod()
                .getDeclaringClass()
                .getAnnotation(DynamicDS.class)
                .value();
        return dataSourceKey;
    }
}
