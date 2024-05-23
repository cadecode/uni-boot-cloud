package com.github.cadecode.uniboot.common.plugin.mybatis.handler;

import java.util.List;

/**
 * 数据权限范围
 *
 * @author Cade Li
 * @since 2024/5/23
 */
public interface DataScopeResolver {

    List<Object> getScopes();

    default boolean isAdmin() {
        return false;
    }
}
