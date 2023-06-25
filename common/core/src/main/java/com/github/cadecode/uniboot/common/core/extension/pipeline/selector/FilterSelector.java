package com.github.cadecode.uniboot.common.core.extension.pipeline.selector;

import java.util.List;

/**
 * 过滤器选择器
 *
 * @author Cade Li
 * @date 2023/6/20
 */
public interface FilterSelector {

    boolean matchFilter(String currFilterName);

    List<String> getFilterNames();
}
