package com.github.cadecode.uniboot.common.core.extension.pipeline;

import com.github.cadecode.uniboot.common.core.enums.ExtTypeCode;
import com.github.cadecode.uniboot.common.core.extension.pipeline.selector.FilterSelector;

/**
 * pipeline 上下文
 *
 * @author Cade Li
 * @since 2023/6/23
 */
public interface PipelineContext {

    ExtTypeCode getPipelineType();

    FilterSelector getFilterSelector();

    boolean continueChain();

}
