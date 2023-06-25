package com.github.cadecode.uniboot.common.core.extension.strategy;

import cn.hutool.core.util.ObjectUtil;
import com.github.cadecode.uniboot.common.core.exception.ExtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    private final PluginRegistry<StrategyService, StrategyContext> pluginRegistry;


    /**
     * 执行匹配的第一个策略，不需要返回值
     *
     * @param clazz    StrategyService 实现类
     * @param context  扩展上下文
     * @param consumer consumer
     */
    @Override
    public <S extends StrategyService> void execute(Class<S> clazz, StrategyContext context, Consumer<S> consumer) {
        List<S> services = selectServices(clazz, context);
        if (ObjectUtil.isNotEmpty(services)) {
            consumer.accept(services.get(0));
            return;
        }
        throw new ExtException("Strategy service not found");
    }

    /**
     * 执行匹配的所以策略，不需要返回值
     *
     * @param clazz    StrategyService 实现类
     * @param context  扩展上下文
     * @param consumer consumer
     */
    @Override
    public <S extends StrategyService> void executeAll(Class<S> clazz, StrategyContext context, Consumer<S> consumer) {
        List<S> services = selectServices(clazz, context);
        if (ObjectUtil.isNotEmpty(services)) {
            services.forEach(consumer);
            return;
        }
        throw new ExtException("Strategy service not found");
    }

    /**
     * 执行匹配的第一个策略，需要返回值
     *
     * @param clazz    StrategyService 实现类
     * @param context  扩展上下文
     * @param function function
     * @return 返回值
     */
    @Override
    public <R, S extends StrategyService> R execute(Class<S> clazz, StrategyContext context, Function<S, R> function) {
        List<S> services = selectServices(clazz, context);
        if (ObjectUtil.isNotEmpty(services)) {
            return function.apply(services.get(0));
        }
        throw new ExtException("Strategy service not found");
    }

    /**
     * 执行匹配的所以策略，需要返回值
     *
     * @param clazz    StrategyService 实现类
     * @param context  扩展上下文
     * @param function function
     * @return 返回值
     */
    @Override
    public <S extends StrategyService> List<Object> executeAll(Class<S> clazz, StrategyContext context, Function<S, Object> function) {
        List<S> services = selectServices(clazz, context);
        if (ObjectUtil.isNotEmpty(services)) {
            return services.stream()
                    .map(function)
                    .collect(Collectors.toList());
        }
        throw new ExtException("Strategy service not found");
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S> List<S> selectServices(Class<S> clazz, StrategyContext context) {
        return (List<S>) pluginRegistry.getPluginsFor(context)
                .stream()
                .filter(o -> clazz.isAssignableFrom(o.getClass()))
                .collect(Collectors.toList());
    }
}
