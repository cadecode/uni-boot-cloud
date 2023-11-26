package com.github.cadecode.uniboot.framework.svc.controller;

import com.github.cadecode.uniboot.framework.base.annotation.ApiFormat;
import com.github.cadecode.uniboot.framework.svc.bean.po.SysDept;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysDeptVo.*;
import com.github.cadecode.uniboot.framework.svc.convert.SysDeptConvert;
import com.github.cadecode.uniboot.framework.svc.service.SysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门管理API
 *
 * @author Cade Li
 * @since 2023/11/24
 */
@ApiFormat
@Slf4j
@RequiredArgsConstructor
@Api(tags = "部门管理")
@RequestMapping("system/dept")
@RestController
@Validated
public class SysDeptController {

    private final SysDeptService sysDeptService;

    @ApiOperation("查询部门列表（树状）")
    @PostMapping("list_tree_vo")
    public List<SysDeptTreeResVo> listTreeVo(@RequestBody @Valid SysDeptTreeReqVo reqVo) {
        return sysDeptService.listTreeVo(reqVo);
    }

    @ApiOperation("更新部门")
    @PostMapping("update")
    public boolean update(@RequestBody @Valid SysDeptUpdateReqVo reqVo) {
        SysDept po = SysDeptConvert.INSTANCE.voToPo(reqVo);
        return sysDeptService.updateById(po);
    }

    @ApiOperation("添加部门")
    @PostMapping("add")
    public boolean add(@RequestBody @Valid SysDeptAddReqVo reqVo) {
        SysDept po = SysDeptConvert.INSTANCE.voToPo(reqVo);
        return sysDeptService.save(po);
    }

    @ApiOperation("删除部门（多选）")
    @PostMapping("delete")
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(@RequestBody @NotEmpty List<Long> deptIdList) {
        List<Long> assumedParentList = new ArrayList<>(deptIdList);
        while (!assumedParentList.isEmpty()) {
            List<SysDept> children = sysDeptService.lambdaQuery()
                    .select(SysDept::getId)
                    .in(SysDept::getParentId, assumedParentList)
                    .list();
            // 加入删除名单
            deptIdList.addAll(children.stream().map(SysDept::getId).collect(Collectors.toList()));
            // 再次查询
            assumedParentList = children.stream()
                    .map(SysDept::getId)
                    .collect(Collectors.toList());
        }
        return sysDeptService.removeBatchByIds(deptIdList);
    }

    @ApiOperation("获取部门 byDeptIds")
    @PostMapping("list_query_vo_by_dept_ids")
    public List<SysDeptQueryResVo> listQueryVoByDeptIds(@RequestBody @NotEmpty List<Long> deptIdList) {
        List<SysDept> poList = sysDeptService.listByIds(deptIdList);
        return SysDeptConvert.INSTANCE.poToQueryResVo(poList);
    }

    @ApiOperation("查询部门列表（搜索建议）")
    @PostMapping("list_parent_suggest")
    public List<SysDeptQueryResVo> listParentSuggest() {
        List<SysDept> poList = sysDeptService.lambdaQuery()
                .select(SysDept::getId, SysDept::getDeptName, SysDept::getOrderNum)
                .list();
        return SysDeptConvert.INSTANCE.poToQueryResVo(poList);
    }
}
