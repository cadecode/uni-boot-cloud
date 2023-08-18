package com.github.cadecode.uniboot.framework.base.plugin.controller;

import cn.hutool.core.util.ObjectUtil;
import com.github.cadecode.uniboot.common.core.web.response.PageResult;
import com.github.cadecode.uniboot.common.plugin.mybatis.converter.BoolToIntTypeHandler;
import com.github.cadecode.uniboot.framework.base.annotation.ApiFormat;
import com.github.cadecode.uniboot.framework.base.annotation.ApiInner;
import com.github.cadecode.uniboot.framework.base.plugin.bean.po.PlgLog;
import com.github.cadecode.uniboot.framework.base.plugin.bean.vo.PlgLogVo;
import com.github.cadecode.uniboot.framework.base.plugin.bean.vo.PlgLogVo.PlgLogPageResVo;
import com.github.cadecode.uniboot.framework.base.plugin.bean.vo.PlgLogVo.PlgLogSaveReqVo;
import com.github.cadecode.uniboot.framework.base.plugin.convert.PlgLogConvert;
import com.github.cadecode.uniboot.framework.base.plugin.service.PlgLogService;
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
 * 日志管理
 *
 * @author Cade Li
 * @since 2023/5/26
 */
@ApiFormat
@Slf4j
@RequiredArgsConstructor
@Api(tags = "日志管理")
@RequestMapping("plugin/log")
@RestController
@Validated
public class PlgLogController {

    private final PlgLogService logService;

    @ApiOperation("查询列表")
    @PostMapping("page")
    public PageResult<PlgLogPageResVo> page(@RequestBody @Valid PlgLogVo.PlgLogPageReqVo reqVo) {
        PageInfo<PlgLog> pageInfo = PageHelper.startPage(reqVo.getPageNumber(), reqVo.getPageSize())
                .doSelectPageInfo(() -> logService.lambdaQuery()
                        .ge(ObjectUtil.isNotEmpty(reqVo.getStartTime()), PlgLog::getCreateTime, reqVo.getStartTime())
                        .le(ObjectUtil.isNotEmpty(reqVo.getEndTime()), PlgLog::getCreateTime, reqVo.getEndTime())
                        .in(ObjectUtil.isNotEmpty(reqVo.getLogTypeList()), PlgLog::getLogType, reqVo.getLogTypeList())
                        .likeRight(ObjectUtil.isNotEmpty(reqVo.getAccessUser()), PlgLog::getAccessUser, reqVo.getAccessUser())
                        .like(ObjectUtil.isNotEmpty(reqVo.getUrl()), PlgLog::getUrl, reqVo.getUrl())
                        .eq(ObjectUtil.isNotNull(reqVo.getExceptional()), PlgLog::getExceptional, BoolToIntTypeHandler.mapping(reqVo.getExceptional()))
                        .orderByDesc(PlgLog::getCreateTime)
                        .list());
        List<PlgLogPageResVo> voList = PlgLogConvert.INSTANCE.poToVo(pageInfo.getList());
        return new PageResult<>((int) pageInfo.getTotal(), voList);
    }

    @ApiOperation("删除")
    @PostMapping("delete")
    public boolean delete(@RequestBody @NotEmpty List<Long> idList) {
        return logService.removeBatchByIds(idList);
    }

    @ApiInner(onlyClient = true)
    @ApiOperation("添加")
    @PostMapping("save")
    public boolean save(@RequestBody @NotEmpty List<PlgLogSaveReqVo> requestList) {
        List<PlgLog> poList = PlgLogConvert.INSTANCE.voToPo(requestList);
        return logService.saveBatch(poList);
    }
}
