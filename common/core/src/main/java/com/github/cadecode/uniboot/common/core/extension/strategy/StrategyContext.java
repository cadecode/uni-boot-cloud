package com.github.cadecode.uniboot.common.core.extension.strategy;

import com.github.cadecode.uniboot.common.core.enums.ExtTypeCode;

/**
 * 策略上下文
 *
 * @author Cade Li
 * @since 2023/6/25
 */
public interface StrategyContext {

    ExtTypeCode getStrategyType();

}
