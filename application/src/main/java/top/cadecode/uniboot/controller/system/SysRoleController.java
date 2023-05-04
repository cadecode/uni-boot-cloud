package top.cadecode.uniboot.controller.system;

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
import top.cadecode.uniboot.system.bean.po.SysRole;
import top.cadecode.uniboot.system.bean.vo.SysRoleVo.SysRoleListVo;
import top.cadecode.uniboot.system.convert.SysRoleConvert;
import top.cadecode.uniboot.system.request.SysRoleRequest.SysRoleMappingRequest;
import top.cadecode.uniboot.system.service.SysRoleService;

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
}
