package com.github.cadecode.uniboot.common.core.extension.pipeline;

import com.github.cadecode.uniboot.common.core.enums.ExtTypeCode;
import com.github.cadecode.uniboot.common.core.extension.pipeline.selector.FilterSelector;
import lombok.Getter;

/**
 * pipeline 上下文抽象类
 *
 * @author Cade Li
 * @since 2023/6/25
 */
@Getter
public abstract class AbstractPipelineContext implements PipelineContext {

    private final ExtTypeCode pipelineType;
    private final FilterSelector filterSelector;

    public AbstractPipelineContext(ExtTypeCode pipelineType, FilterSelector filterSelector) {
        this.pipelineType = pipelineType;
        this.filterSelector = filterSelector;
    }

}
