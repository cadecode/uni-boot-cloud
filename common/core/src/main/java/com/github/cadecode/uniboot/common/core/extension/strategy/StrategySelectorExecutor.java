package com.github.cadecode.uniboot.common.core.extension.strategy;

import com.github.cadecode.uniboot.common.core.exception.ExtException;
import com.github.cadecode.uniboot.common.core.extension.ExtContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 策略执行器
 *
 * @author Cade Li
 * @since 2023/6/23
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class StrategySelectorExecutor extends AbstractStrategyExecutor {

    private final PluginRegistry<StrategyService, ExtContext> pluginRegistry;


    /**
     * 执行，不需要返回值
     *
     * @param clazz    StrategyService 实现类
     * @param context  扩展上下文
     * @param consumer consumer
     */
    @Override
    public <S extends StrategyService> void execute(Class<S> clazz, ExtContext context, Consumer<S> consumer) {
        Optional<S> serviceOpt = selectService(clazz, context);
        if (serviceOpt.isPresent()) {
            consumer.accept(serviceOpt.get());
            return;
        }
        throw new ExtException("Strategy service not found");
    }

    /**
     * 执行，需要返回值
     *
     * @param clazz    StrategyService 实现类
     * @param context  扩展上下文
     * @param function function
     * @return 返回值
     */
    @Override
    public <R, S extends StrategyService> R execute(Class<S> clazz, ExtContext context, Function<S, R> function) {
        Optional<S> serviceOpt = selectService(clazz, context);
        if (serviceOpt.isPresent()) {
            return function.apply(serviceOpt.get());
        }
        throw new ExtException("Strategy service not found");
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S> Optional<S> selectService(Class<S> clazz, ExtContext context) {
        return (Optional<S>) pluginRegistry.getPluginsFor(context)
                .stream()
                .filter(o -> clazz.isAssignableFrom(o.getClass()))
                .findAny();
    }
}
