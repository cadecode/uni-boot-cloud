package com.github.cadecode.uniboot.common.plugin.datasource.util;

import org.apache.shardingsphere.infra.hint.HintManager;

import java.util.function.Consumer;

/**
 * ShardingJdbc hint 工具类
 *
 * @author Cade Li
 * @since 2023/12/1
 */
public class ShardingHintUtil {

    public static HintManager setWriteRouteOnly() {
        HintManager.clear();
        HintManager hintManager = HintManager.getInstance();
        hintManager.setWriteRouteOnly();
        return hintManager;
    }

    public static void doWriteRouteOnly(Consumer<HintManager> consumer) {
        HintManager.clear();
        try (HintManager hintManager = HintManager.getInstance()) {
            hintManager.setWriteRouteOnly();
            consumer.accept(hintManager);
        }
    }
}
