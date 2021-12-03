package top.cadecode.common.datasource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
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
    @Before("@annotation(dataSource)")
    public void switchDataSource(JoinPoint point, DataSource dataSource) {
        String dataSourceKey = dataSource.value();
        if (!DynamicDataSourceHolder.containDataSourceKey(dataSourceKey)) {
            log.info("数据源 {} 不存在，将使用默认数据源", dataSource.value());
        } else {
            // 切换数据源
            DynamicDataSourceHolder.setDataSourceKey(dataSource.value());
            log.info("切换数据源到 {}，在方法 [{}]", dataSourceKey, point.getSignature());
        }
    }

    /**
     * 重置数据源
     */
    @After("@annotation(dataSource)")
    public void resetDataSource(JoinPoint point, DataSource dataSource) {
        // 将数据源重置置为默认数据源
        DynamicDataSourceHolder.clearDataSourceKey();
        log.info("重置默认数据源，在方法 [{}]", point.getSignature());
    }

}
