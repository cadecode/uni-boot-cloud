package com.github.cadecode.uniboot.common.core.extension.strategy;

import com.github.cadecode.uniboot.common.core.extension.ExtContext;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 策略执行器接口
 *
 * @author Cade Li
 * @since 2023/6/24
 */
public interface StrategyExecutor {

    <S extends StrategyService> void execute(Class<S> clazz, ExtContext context, Consumer<S> consumer);

    <R, S extends StrategyService> R execute(Class<S> clazz, ExtContext context, Function<S, R> function);
}
