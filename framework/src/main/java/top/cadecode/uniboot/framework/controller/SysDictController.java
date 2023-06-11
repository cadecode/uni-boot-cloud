package top.cadecode.uniboot.framework.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.cadecode.uniboot.common.core.web.response.PageResult;
import top.cadecode.uniboot.framework.annotation.ApiFormat;
import top.cadecode.uniboot.framework.bean.po.SysDict;
import top.cadecode.uniboot.framework.bean.vo.SysDictVo.SysDictGetByTypeVo;
import top.cadecode.uniboot.framework.bean.vo.SysDictVo.SysDictPageVo;
import top.cadecode.uniboot.framework.bean.vo.SysDictVo.SysDictSuggestVo;
import top.cadecode.uniboot.framework.convert.SysDictConvert;
import top.cadecode.uniboot.framework.request.SysDictRequest.SysDictAddRequest;
import top.cadecode.uniboot.framework.request.SysDictRequest.SysDictPageRequest;
import top.cadecode.uniboot.framework.request.SysDictRequest.SysDictUpdateRequest;
import top.cadecode.uniboot.framework.service.SysDictService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    public PageResult<SysDictPageVo> page(@RequestBody @Valid SysDictPageRequest request) {
        Page<SysDict> page = sysDictService.lambdaQuery()
                .likeRight(ObjectUtil.isNotEmpty(request.getName()), SysDict::getName, request.getName())
                .likeRight(ObjectUtil.isNotEmpty(request.getType()), SysDict::getType, request.getType())
                .orderByDesc(SysDict::getCreateTime)
                .page(new Page<>(request.getPageNumber(), request.getPageSize()));
        List<SysDictPageVo> voList = SysDictConvert.INSTANCE.poToPageVo(page.getRecords());
        return new PageResult<>((int) page.getTotal(), voList);
    }

    @ApiOperation("添加")
    @PostMapping("add")
    public boolean add(@RequestBody @Valid SysDictAddRequest request) {
        SysDict po = SysDictConvert.INSTANCE.requestToPo(request);
        return sysDictService.save(po);
    }

    @ApiOperation("更新")
    @PostMapping("update")
    public boolean update(@RequestBody @Valid SysDictUpdateRequest request) {
        SysDict po = SysDictConvert.INSTANCE.requestToPo(request);
        return sysDictService.updateById(po);
    }

    @ApiOperation("删除")
    @PostMapping("delete")
    public boolean delete(@RequestBody @NotEmpty List<Long> idList) {
        return sysDictService.removeBatchByIds(idList);
    }

    @ApiOperation("查询 ByType")
    @GetMapping("list_by_type")
    public List<SysDictGetByTypeVo> listByType(@RequestParam String type) {
        List<SysDict> dictList = sysDictService.lambdaQuery().eq(SysDict::getType, type).list();
        return SysDictConvert.INSTANCE.poToGetByTypeVo(dictList);
    }

    @ApiOperation("查询 ByIds")
    @PostMapping("list_by_ids")
    public List<SysDictPageVo> listByIds(@RequestBody @NotEmpty List<Long> idList) {
        List<SysDict> dictList = sysDictService.listByIds(idList);
        return SysDictConvert.INSTANCE.poToPageVo(dictList);
    }

    @ApiOperation("查询字典类型种类")
    @PostMapping("list_type")
    public List<SysDictSuggestVo> listType() {
        List<SysDict> dictList = sysDictService.lambdaQuery().select(SysDict::getName, SysDict::getType).list();
        return dictList.stream()
                .map(SysDictConvert.INSTANCE::poToSuggestVo)
                .sorted(Comparator.comparing(SysDictSuggestVo::getType))
                .distinct()
                .collect(Collectors.toList());
    }
}
