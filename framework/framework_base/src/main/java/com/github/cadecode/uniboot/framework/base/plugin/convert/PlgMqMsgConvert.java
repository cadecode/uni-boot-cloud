package com.github.cadecode.uniboot.framework.base.plugin.convert;

import com.github.cadecode.uniboot.common.plugin.mq.config.TxMsgProperties.MsgOption;
import com.github.cadecode.uniboot.common.plugin.mq.model.BaseTxMsg;
import com.github.cadecode.uniboot.common.plugin.mq.model.TxMsg;
import com.github.cadecode.uniboot.framework.base.plugin.bean.po.PlgMqMsg;
import com.github.cadecode.uniboot.framework.base.plugin.bean.vo.PlgMqMsgVo.PlgMqMsgPageResVo;
import com.github.cadecode.uniboot.framework.base.plugin.bean.vo.PlgMqMsgVo.PlgMqMsgUpdateReqVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

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
    @Mapping(target = "leftRetryTimes", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "consumeState", ignore = true)
    @Mapping(target = "cause", ignore = true)
    PlgMqMsg baseTxMsgToPo(BaseTxMsg txMsg, MsgOption msgOption);

    TxMsg poToTxMsg(PlgMqMsg po);

    MsgOption poToMsgOption(PlgMqMsg po);

    PlgMqMsgPageResVo poToPageResVo(PlgMqMsg po);

    List<PlgMqMsgPageResVo> poToPageResVo(List<PlgMqMsg> poList);

    @Mapping(target = "connectionName", ignore = true)
    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "routingKey", ignore = true)
    @Mapping(target = "nextRetryTime", ignore = true)
    @Mapping(target = "message", ignore = true)
    @Mapping(target = "maxRetryTimes", ignore = true)
    @Mapping(target = "exchange", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "consumeState", ignore = true)
    @Mapping(target = "cause", ignore = true)
    @Mapping(target = "bizType", ignore = true)
    @Mapping(target = "bizKey", ignore = true)
    @Mapping(target = "backoffMultiplier", ignore = true)
    @Mapping(target = "backoffMaxInterval", ignore = true)
    @Mapping(target = "backoffInitInterval", ignore = true)
    PlgMqMsg voToPo(PlgMqMsgUpdateReqVo reqVo);
}
