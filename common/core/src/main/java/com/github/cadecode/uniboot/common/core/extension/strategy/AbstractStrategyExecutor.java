package com.github.cadecode.uniboot.common.core.extension.strategy;

import java.util.List;
import java.util.Optional;

/**
 * 策略执行器抽象类
 *
 * @author Cade Li
 * @since 2023/6/24
 */
public abstract class AbstractStrategyExecutor implements StrategyExecutor {

    public abstract <S> Optional<S> selectService(Class<S> clazz, StrategyContext context);

    public abstract <S> List<S> selectServices(Class<S> clazz, StrategyContext context);
}
