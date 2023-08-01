package com.github.cadecode.uniboot.framework.svc.controller;

import cn.hutool.core.util.ObjectUtil;
import com.github.cadecode.uniboot.common.core.web.response.PageResult;
import com.github.cadecode.uniboot.common.plugin.mybatis.converter.BoolToIntTypeHandler;
import com.github.cadecode.uniboot.framework.api.annotation.ApiFormat;
import com.github.cadecode.uniboot.framework.api.bean.dto.SysLogDto.SysLogInfoDto;
import com.github.cadecode.uniboot.framework.api.bean.po.SysLog;
import com.github.cadecode.uniboot.framework.api.bean.vo.SysLogVo.SysLogPageVo;
import com.github.cadecode.uniboot.framework.api.convert.SysLogConvert;
import com.github.cadecode.uniboot.framework.api.request.SysLogRequest.SysLogPageRequest;
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
    public PageResult<SysLogPageVo> page(@RequestBody @Valid SysLogPageRequest request) {
        PageInfo<SysLog> pageInfo = PageHelper.startPage(request.getPageNumber(), request.getPageSize())
                .doSelectPageInfo(() -> logService.lambdaQuery()
                        .ge(ObjectUtil.isNotEmpty(request.getStartTime()), SysLog::getCreateTime, request.getStartTime())
                        .le(ObjectUtil.isNotEmpty(request.getEndTime()), SysLog::getCreateTime, request.getEndTime())
                        .in(ObjectUtil.isNotEmpty(request.getLogTypeList()), SysLog::getLogType, request.getLogTypeList())
                        .likeRight(ObjectUtil.isNotEmpty(request.getAccessUser()), SysLog::getAccessUser, request.getAccessUser())
                        .like(ObjectUtil.isNotEmpty(request.getUrl()), SysLog::getUrl, request.getUrl())
                        .eq(ObjectUtil.isNotNull(request.getExceptional()), SysLog::getExceptional, BoolToIntTypeHandler.mapping(request.getExceptional()))
                        .orderByDesc(SysLog::getCreateTime)
                        .list());
        List<SysLogPageVo> voList = SysLogConvert.INSTANCE.poToVo(pageInfo.getList());
        return new PageResult<>((int) pageInfo.getTotal(), voList);
    }

    @ApiOperation("删除")
    @PostMapping("delete")
    public boolean delete(@RequestBody @NotEmpty List<Long> idList) {
        return logService.removeBatchByIds(idList);
    }

    // For feign client
    @ApiFormat(false)
    @ApiOperation("添加")
    @PostMapping("save")
    public boolean save(@RequestBody @NotEmpty List<SysLogInfoDto> dtoList) {
        List<SysLog> poList = SysLogConvert.INSTANCE.dtoToPo(dtoList);
        return logService.saveBatch(poList);
    }
}
