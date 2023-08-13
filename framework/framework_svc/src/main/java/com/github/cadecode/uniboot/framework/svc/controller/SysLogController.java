package com.github.cadecode.uniboot.framework.svc.controller;

import cn.hutool.core.util.ObjectUtil;
import com.github.cadecode.uniboot.common.core.web.response.PageResult;
import com.github.cadecode.uniboot.common.plugin.mybatis.converter.BoolToIntTypeHandler;
import com.github.cadecode.uniboot.framework.base.annotation.ApiFormat;
import com.github.cadecode.uniboot.framework.base.annotation.ApiInner;
import com.github.cadecode.uniboot.framework.svc.bean.po.SysLog;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysLogVo.SysLogPageResVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysLogVo.SysLogSaveReqVo;
import com.github.cadecode.uniboot.framework.svc.convert.SysLogConvert;
import com.github.cadecode.uniboot.framework.svc.service.SysLogService;
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

import static com.github.cadecode.uniboot.framework.svc.bean.vo.SysLogVo.SysLogPageReqVo;

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
@RequestMapping("system/log")
@RestController
@Validated
public class SysLogController {

    private final SysLogService logService;

    @ApiOperation("查询列表")
    @PostMapping("page")
    public PageResult<SysLogPageResVo> page(@RequestBody @Valid SysLogPageReqVo reqVo) {
        PageInfo<SysLog> pageInfo = PageHelper.startPage(reqVo.getPageNumber(), reqVo.getPageSize())
                .doSelectPageInfo(() -> logService.lambdaQuery()
                        .ge(ObjectUtil.isNotEmpty(reqVo.getStartTime()), SysLog::getCreateTime, reqVo.getStartTime())
                        .le(ObjectUtil.isNotEmpty(reqVo.getEndTime()), SysLog::getCreateTime, reqVo.getEndTime())
                        .in(ObjectUtil.isNotEmpty(reqVo.getLogTypeList()), SysLog::getLogType, reqVo.getLogTypeList())
                        .likeRight(ObjectUtil.isNotEmpty(reqVo.getAccessUser()), SysLog::getAccessUser, reqVo.getAccessUser())
                        .like(ObjectUtil.isNotEmpty(reqVo.getUrl()), SysLog::getUrl, reqVo.getUrl())
                        .eq(ObjectUtil.isNotNull(reqVo.getExceptional()), SysLog::getExceptional, BoolToIntTypeHandler.mapping(reqVo.getExceptional()))
                        .orderByDesc(SysLog::getCreateTime)
                        .list());
        List<SysLogPageResVo> voList = SysLogConvert.INSTANCE.poToVo(pageInfo.getList());
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
    public boolean save(@RequestBody @NotEmpty List<SysLogSaveReqVo> requestList) {
        List<SysLog> poList = SysLogConvert.INSTANCE.voToPo(requestList);
        return logService.saveBatch(poList);
    }
}
