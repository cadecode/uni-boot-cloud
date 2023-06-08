package top.cadecode.uniboot.framework.controller;

import com.github.pagehelper.PageInfo;
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
import top.cadecode.uniboot.common.core.web.response.PageResult;
import top.cadecode.uniboot.framework.annotation.ApiFormat;
import top.cadecode.uniboot.framework.bean.po.SysRole;
import top.cadecode.uniboot.framework.bean.vo.SysRoleVo.SysRoleListVo;
import top.cadecode.uniboot.framework.bean.vo.SysRoleVo.SysRoleUnionVo;
import top.cadecode.uniboot.framework.convert.SysRoleConvert;
import top.cadecode.uniboot.framework.request.SysRoleRequest;
import top.cadecode.uniboot.framework.request.SysRoleRequest.SysRoleMappingRequest;
import top.cadecode.uniboot.framework.request.SysRoleRequest.SysRoleUnionRequest;
import top.cadecode.uniboot.framework.service.SysRoleService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 角色管理API
 *
 * @author Cade Li
 * @since 2023/5/4
 */
@ApiFormat
@Slf4j
@RequiredArgsConstructor
@Api(tags = "角色管理")
@RequestMapping("system/role")
@RestController
@Validated
public class SysRoleController {

    private final SysRoleService sysRoleService;

    @ApiOperation("查询角色列表")
    @PostMapping("list")
    public List<SysRoleListVo> roleList() {
        List<SysRole> roleList = sysRoleService.list();
        return SysRoleConvert.INSTANCE.poToListVo(roleList);
    }

    @ApiOperation("添加用户角色绑定")
    @PostMapping("add_user_mapping")
    public boolean addUserMapping(@RequestBody @NotEmpty List<SysRoleMappingRequest> request) {
        return sysRoleService.addRoleUser(request) > 0;
    }

    @ApiOperation("删除用户角色绑定")
    @PostMapping("remove_user_mapping")
    public boolean removeUserMapping(@RequestBody @NotEmpty List<SysRoleMappingRequest> request) {
        return sysRoleService.removeRoleUser(request) > 0;
    }

    @ApiOperation("添加菜单角色绑定")
    @PostMapping("add_menu_mapping")
    public boolean addMenuMapping(@RequestBody @NotEmpty List<SysRoleMappingRequest> request) {
        return sysRoleService.addRoleMenu(request) > 0;
    }

    @ApiOperation("删除菜单角色绑定")
    @PostMapping("remove_menu_mapping")
    public boolean removeMenuMapping(@RequestBody @NotEmpty List<SysRoleMappingRequest> request) {
        return sysRoleService.removeRoleMenu(request) > 0;
    }

    @ApiOperation("添加API角色绑定")
    @PostMapping("add_api_mapping")
    public boolean addApiMapping(@RequestBody @NotEmpty List<SysRoleMappingRequest> request) {
        return sysRoleService.addRoleApi(request) > 0;
    }

    @ApiOperation("删除API角色绑定")
    @PostMapping("remove_api_mapping")
    public boolean removeApiMapping(@RequestBody @NotEmpty List<SysRoleMappingRequest> request) {
        return sysRoleService.removeRoleApi(request) > 0;
    }

    @ApiOperation("查询角色列表（带菜单、api）")
    @PostMapping("page_union_vo")
    public PageResult<SysRoleUnionVo> pageUnionVo(@RequestBody @Valid SysRoleUnionRequest request) {
        PageInfo<SysRoleUnionVo> page = sysRoleService.pageUnionVo(request);
        return new PageResult<>((int) page.getTotal(), page.getList());
    }

    @ApiOperation("查询角色列表（带菜单、api）byRoleIds")
    @PostMapping("list_union_vo_by_role_ids")
    public List<SysRoleUnionVo> listUnionVoByRoleIds(@RequestBody @NotEmpty List<Long> roleIds) {
        return sysRoleService.listUnionVoByRoleIds(roleIds);
    }

    @ApiOperation("更新角色")
    @PostMapping("update")
    public boolean update(@RequestBody @Valid SysRoleRequest.SysRoleUpdateRequest request) {
        SysRole po = SysRoleConvert.INSTANCE.requestToPo(request);
        return sysRoleService.updateById(po);
    }

    @ApiOperation("添加角色")
    @PostMapping("add")
    public boolean add(@RequestBody @Valid SysRoleRequest.SysRoleAddRequest request) {
        SysRole sysRole = SysRoleConvert.INSTANCE.requestToPo(request);
        return sysRoleService.save(sysRole);
    }

    @ApiOperation("删除角色（多选）")
    @PostMapping("delete")
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(@RequestBody @NotEmpty List<Long> roleIdList) {
        // 清理角色绑定关系
        sysRoleService.removeRoleApiByRoleIds(roleIdList);
        sysRoleService.removeRoleMenuByRoleIds(roleIdList);
        sysRoleService.removeRoleUserByRoleIds(roleIdList);
        return sysRoleService.removeBatchByIds(roleIdList);
    }
}
