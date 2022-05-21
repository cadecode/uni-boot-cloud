package top.cadecode.sra.framework.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
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
@Order(-1) // 设置 Order 使切面在 @Transactional 之前执行
public class DynamDSAspect {

    /**
     * 切换数据源
     */
    @Before("@within(ds) || @annotation(ds)")
    public void switchDataSource(JoinPoint point, DynamicDS ds) {
        // 获取方法上的注解
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        DynamicDS dsM = methodSignature.getMethod().getAnnotation(DynamicDS.class);
        // 获取数据源 key
        String dataSourceKey;
        if (dsM != null) {
            dataSourceKey = dsM.value();
        } else {
            // 获取取类上的注解内容
            dataSourceKey = methodSignature.getMethod()
                    .getDeclaringClass().
                    getAnnotation(DynamicDS.class)
                    .value();
        }
        // 设置数据源
        if (!DynamicDSHolder.containDataSourceKey(dataSourceKey)) {
            log.info("切换数据源 => 数据源 {} 不存在，使用默认数据源", ds.value());
        } else {
            DynamicDSHolder.setDataSourceKey(dataSourceKey);
            log.info("切换数据源 => 切换到 {}，执行方法 [{}]", dataSourceKey,
                    point.getSignature().getDeclaringTypeName() + '.' + point.getSignature().getName());
        }
    }

    /**
     * 重置数据源
     */
    @After("@within(ds) || @annotation(ds)")
    public void resetDataSource(JoinPoint point, DynamicDS ds) {
        // 将数据源重置置为默认数据源
        DynamicDSHolder.clearDataSourceKey();
        log.info("切换数据源 <= 重置默认数据源");
    }
}
