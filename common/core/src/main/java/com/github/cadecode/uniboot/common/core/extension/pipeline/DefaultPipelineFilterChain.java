package com.github.cadecode.uniboot.common.core.extension.pipeline;

import com.github.cadecode.uniboot.common.core.extension.pipeline.filter.PipelineFilter;
import lombok.Setter;

import java.util.Objects;

/**
 * 过滤器链默认实现
 *
 * @author Cade Li
 * @date 2023/6/20
 */
public class DefaultPipelineFilterChain<T extends PipelineContext> implements PipelineFilterChain<T> {

    @Setter
    private PipelineFilterChain<T> next;
    private final PipelineFilter<T> filter;

    public DefaultPipelineFilterChain(PipelineFilterChain<T> next, PipelineFilter<T> filter) {
        this.next = next;
        this.filter = filter;
    }


    /**
     * 当前 filter 处理
     */
    @Override
    public void filter(T context) {
        filter.doFilter(context, this);
    }

    /**
     * 下一 filter 处理
     */
    @Override
    public void next(T context) {
        if (Objects.nonNull(this.next)) {
            this.next.filter(context);
        }
    }
}
