package com.github.cadecode.uniboot.framework.base.plugin.convert;

import com.github.cadecode.uniboot.common.plugin.mq.config.TxMsgProperties.MsgOption;
import com.github.cadecode.uniboot.common.plugin.mq.model.BaseTxMsg;
import com.github.cadecode.uniboot.common.plugin.mq.model.TxMsg;
import com.github.cadecode.uniboot.framework.base.plugin.bean.po.PlgMqMsg;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * PlgMqMsg Bean Convert
 *
 * @author Cade Li
 * @since 2023/8/19
 */
@Mapper
public interface PlgMqMsgConvert {

    PlgMqMsgConvert INSTANCE = Mappers.getMapper(PlgMqMsgConvert.class);

    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "sendState", ignore = true)
    @Mapping(target = "nextRetryTime", ignore = true)
    @Mapping(target = "currRetryTimes", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "consumeState", ignore = true)
    @Mapping(target = "cause", ignore = true)
    PlgMqMsg baseTxMsgToPo(BaseTxMsg txMsg, MsgOption msgOption);

    TxMsg poToTxMsg(PlgMqMsg po);

    MsgOption poToMsgOption(PlgMqMsg po);

}
