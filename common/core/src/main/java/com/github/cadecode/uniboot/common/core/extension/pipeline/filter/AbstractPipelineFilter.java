package com.github.cadecode.uniboot.common.core.extension.pipeline.filter;

import com.github.cadecode.uniboot.common.core.extension.pipeline.PipelineContext;
import com.github.cadecode.uniboot.common.core.extension.pipeline.PipelineFilterChain;

/**
 * 过滤器抽象类
 *
 * @author Cade Li
 * @date 2023/6/20
 */
public abstract class AbstractPipelineFilter<T extends PipelineContext> implements PipelineFilter<T> {

    /**
     * 过滤方法模板
     */
    @Override
    public void doFilter(T context, PipelineFilterChain<T> filterChain) {
        // 如果包含该 filter
        if (context.getFilterSelector().matchFilter(this.getClass().getSimpleName())) {
            handle(context);
        }
        if (context.continueChain()) {
            filterChain.next(context);
        }
    }

    /**
     * 过滤处理主逻辑
     */
    public abstract void handle(T context);
}
