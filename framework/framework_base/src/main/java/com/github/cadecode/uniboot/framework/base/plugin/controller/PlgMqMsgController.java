package com.github.cadecode.uniboot.framework.base.plugin.controller;

import cn.hutool.core.util.ObjUtil;
import com.github.cadecode.uniboot.common.core.web.response.PageResult;
import com.github.cadecode.uniboot.framework.base.annotation.ApiFormat;
import com.github.cadecode.uniboot.framework.base.plugin.bean.po.PlgMqMsg;
import com.github.cadecode.uniboot.framework.base.plugin.bean.vo.PlgMqMsgVo.PlgMqMsgPageReqVo;
import com.github.cadecode.uniboot.framework.base.plugin.bean.vo.PlgMqMsgVo.PlgMqMsgPageResVo;
import com.github.cadecode.uniboot.framework.base.plugin.bean.vo.PlgMqMsgVo.PlgMqMsgUpdateReqVo;
import com.github.cadecode.uniboot.framework.base.plugin.convert.PlgMqMsgConvert;
import com.github.cadecode.uniboot.framework.base.plugin.service.PlgMqMsgService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * MQ 消息管理
 *
 * @author Cade Li
 * @since 2023/8/19
 */
@ApiFormat
@Slf4j
@RequiredArgsConstructor
@Api(tags = "MQ 管理")
@RequestMapping("plugin/mq_msg")
@RestController
@Validated
public class PlgMqMsgController {

    private final PlgMqMsgService mqMsgService;

    @ApiOperation("查询列表")
    @PostMapping("page")
    public PageResult<PlgMqMsgPageResVo> page(@RequestBody @Valid PlgMqMsgPageReqVo reqVo) {
        PageInfo<PlgMqMsg> pageInfo = PageHelper.startPage(reqVo.getPageNumber(), reqVo.getPageSize())
                .doSelectPageInfo(() -> mqMsgService.lambdaQuery()
                        .ge(ObjUtil.isNotEmpty(reqVo.getStartCreateTime()), PlgMqMsg::getCreateTime, reqVo.getStartCreateTime())
                        .le(ObjUtil.isNotEmpty(reqVo.getEndCreateTime()), PlgMqMsg::getCreateTime, reqVo.getEndCreateTime())
                        .eq(ObjUtil.isNotEmpty(reqVo.getBizType()), PlgMqMsg::getBizType, reqVo.getBizType())
                        .in(ObjUtil.isNotEmpty(reqVo.getSendStateList()), PlgMqMsg::getSendState, reqVo.getSendStateList())
                        .orderByDesc(PlgMqMsg::getCreateTime)
                        .list());
        List<PlgMqMsgPageResVo> voList = PlgMqMsgConvert.INSTANCE.poToPageResVo(pageInfo.getList());
        return new PageResult<>((int) pageInfo.getTotal(), voList);
    }

    @ApiOperation("更新")
    @PostMapping("update")
    public boolean update(@RequestBody @Valid PlgMqMsgUpdateReqVo reqVo) {
        PlgMqMsg po = PlgMqMsgConvert.INSTANCE.voToPo(reqVo);
        return mqMsgService.updateById(po);
    }

    @ApiOperation("查询-byIdList")
    @PostMapping("list_by_id_list")
    public List<PlgMqMsgPageResVo> listByIdList(@RequestBody @NotEmpty List<String> idList) {
        List<PlgMqMsg> poList = mqMsgService.listByIds(idList);
        return PlgMqMsgConvert.INSTANCE.poToPageResVo(poList);
    }
}
