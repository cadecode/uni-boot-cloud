package com.github.cadecode.uniboot.framework.svc.controller;

import cn.hutool.core.util.ObjUtil;
import com.github.cadecode.uniboot.common.core.web.response.PageResult;
import com.github.cadecode.uniboot.framework.base.annotation.ApiFormat;
import com.github.cadecode.uniboot.framework.svc.bean.po.SysDict;
import com.github.cadecode.uniboot.framework.svc.convert.SysDictConvert;
import com.github.cadecode.uniboot.framework.svc.service.SysDictService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.cadecode.uniboot.framework.svc.bean.vo.SysDictVo.*;

/**
 * 日志管理
 *
 * @author Cade Li
 * @since 2023/5/26
 */
@ApiFormat
@Slf4j
@RequiredArgsConstructor
@Api(tags = "字典管理")
@RequestMapping("system/dict")
@RestController
@Validated
public class SysDictController {

    private final SysDictService sysDictService;

    @ApiOperation("查询列表")
    @PostMapping("page")
    public PageResult<SysDictPageResVo> page(@RequestBody @Valid SysDictPageReqVo reqVo) {
        PageInfo<SysDict> pageInfo = PageHelper.startPage(reqVo.getPageNumber(), reqVo.getPageSize())
                .doSelectPageInfo(() -> sysDictService.lambdaQuery()
                        .likeRight(ObjUtil.isNotEmpty(reqVo.getName()), SysDict::getName, reqVo.getName())
                        .likeRight(ObjUtil.isNotEmpty(reqVo.getType()), SysDict::getType, reqVo.getType())
                        .orderByDesc(SysDict::getCreateTime)
                        .list());
        List<SysDictPageResVo> voList = SysDictConvert.INSTANCE.poToPageResVo(pageInfo.getList());
        return new PageResult<>((int) pageInfo.getTotal(), voList);
    }

    @ApiOperation("添加")
    @PostMapping("add")
    public boolean add(@RequestBody @Valid SysDictAddReqVo reqVo) {
        SysDict po = SysDictConvert.INSTANCE.voToPo(reqVo);
        return sysDictService.save(po);
    }

    @ApiOperation("更新")
    @PostMapping("update")
    public boolean update(@RequestBody @Valid SysDictUpdateReqVo reqVo) {
        SysDict po = SysDictConvert.INSTANCE.voToPo(reqVo);
        return sysDictService.updateById(po);
    }

    @ApiOperation("删除")
    @PostMapping("delete")
    public boolean delete(@RequestBody @NotEmpty List<Long> idList) {
        return sysDictService.removeBatchByIds(idList);
    }

    @ApiOperation("查询 ByType")
    @GetMapping("list_by_type")
    public List<SysDictGetByTypeResVo> listByType(@RequestParam String type) {
        List<SysDict> dictList = sysDictService.lambdaQuery().eq(SysDict::getType, type).list();
        return SysDictConvert.INSTANCE.poToGetByTypeResVo(dictList);
    }

    @ApiOperation("查询 ByIds")
    @PostMapping("list_by_ids")
    public List<SysDictPageResVo> listByIds(@RequestBody @NotEmpty List<Long> idList) {
        List<SysDict> dictList = sysDictService.listByIds(idList);
        return SysDictConvert.INSTANCE.poToPageResVo(dictList);
    }

    @ApiOperation("查询字典类型种类")
    @PostMapping("list_type")
    public List<SysDictSuggestResVo> listType() {
        List<SysDict> dictList = sysDictService.lambdaQuery().select(SysDict::getName, SysDict::getType).list();
        return dictList.stream()
                .map(SysDictConvert.INSTANCE::poToSuggestResVo)
                .sorted(Comparator.comparing(SysDictSuggestResVo::getType))
                .distinct()
                .collect(Collectors.toList());
    }
}
