package top.cadecode.common.datasource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import top.cadecode.common.datasource.DynamicDataSource.DynamicDataSourceHolder;

/**
 * @author Li Jun
 * @date 2021/12/3
 * @description 动态数据源切换 AOP 类
 */
@Slf4j
@Aspect
@Component
@Order(-1) // 设置 Order 使切面在 @Transactional 之前执行
public class DynamicDataSourceAspect {

    /**
     * 切换数据源
     */
    @Before("@within(dataSource) || @annotation(dataSource)")
    public void switchDataSource(JoinPoint point, DataSource dataSource) {
        // 获取方法上的注解
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        DataSource mDataSource = methodSignature.getMethod().getAnnotation(DataSource.class);
        // 获取数据源 key
        String dataSourceKey;
        if (mDataSource != null) {
            dataSourceKey = mDataSource.value();
        } else {
            // 获取取类上的注解内容
            dataSourceKey = methodSignature.getMethod()
                    .getDeclaringClass().
                    getAnnotation(DataSource.class)
                    .value();
        }
        // 设置数据源
        if (!DynamicDataSourceHolder.containDataSourceKey(dataSourceKey)) {
            log.info("数据源 {} 不存在，将使用默认数据源", dataSource.value());
        } else {
            DynamicDataSourceHolder.setDataSourceKey(dataSourceKey);
            log.info("切换数据源到 {}，在方法 [{}]", dataSourceKey, point.getSignature());
        }
    }

    /**
     * 重置数据源
     */
    @After("@within(dataSource) || @annotation(dataSource)")
    public void resetDataSource(JoinPoint point, DataSource dataSource) {
        // 将数据源重置置为默认数据源
        DynamicDataSourceHolder.clearDataSourceKey();
        log.info("重置默认数据源，在方法 [{}]", point.getSignature());
    }
}
