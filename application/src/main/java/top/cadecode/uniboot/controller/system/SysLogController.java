package top.cadecode.uniboot.controller.system;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cadecode.uniboot.common.annotation.ApiFormat;
import top.cadecode.uniboot.common.response.PageResult;
import top.cadecode.uniboot.system.bean.po.SysLog;
import top.cadecode.uniboot.system.bean.vo.SysLogVo.SysLogPageVo;
import top.cadecode.uniboot.system.convert.SysLogConvert;
import top.cadecode.uniboot.system.request.SysLogRequest.SysLogPageRequest;
import top.cadecode.uniboot.system.service.SysLogService;

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
        Page<SysLog> page = logService.lambdaQuery()
                .setEntity(SysLog.builder()
                        .exceptional(request.getExceptional())
                        .build())
                .eq(ObjectUtil.isNotEmpty(request.getCreateTime()), SysLog::getCreateTime, request.getCreateTime())
                .in(ObjectUtil.isNotEmpty(request.getLogTypeList()), SysLog::getLogType, request.getLogTypeList())
                .likeRight(ObjectUtil.isNotEmpty(request.getAccessUser()), SysLog::getAccessUser, request.getAccessUser())
                .like(ObjectUtil.isNotEmpty(request.getUrl()), SysLog::getUrl, request.getUrl())
                .orderByDesc(SysLog::getCreateTime)
                .page(new Page<>(request.getPageNumber(), request.getPageSize()));
        List<SysLogPageVo> voList = SysLogConvert.INSTANCE.poToVo(page.getRecords());
        return new PageResult<>((int) page.getTotal(), voList);
    }

    @ApiOperation("查询列表")
    @PostMapping("delete")
    public boolean delete(@RequestBody @NotEmpty List<Long> idList) {
        return logService.removeBatchByIds(idList);
    }

}
