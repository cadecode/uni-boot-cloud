package com.github.cadecode.uniboot.common.core.extension.pipeline.selector;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 基于本地 list 的过滤器选择器
 *
 * @author Cade Li
 * @date 2023/6/20
 */
@NoArgsConstructor
@AllArgsConstructor
public class LocalListFilterSelector implements FilterSelector {

    private List<String> filterNames = new ArrayList<>();

    @Override
    public boolean matchFilter(String currFilterName) {
        return filterNames.stream().anyMatch(o -> Objects.equals(currFilterName, o));
    }

    @Override
    public List<String> getFilterNames() {
        return filterNames;
    }

    public void addFilter(String filterName) {
        filterNames.add(filterName);
    }

    private void addFilters(List<String> filterNames) {
        this.filterNames.addAll(filterNames);
    }
}
