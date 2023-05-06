package top.cadecode.uniboot.controller.system;

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
import top.cadecode.uniboot.common.annotation.ApiFormat;
import top.cadecode.uniboot.common.response.PageResult;
import top.cadecode.uniboot.system.bean.po.SysMenu;
import top.cadecode.uniboot.system.bean.vo.SysMenuVo.SysMenuRolesVo;
import top.cadecode.uniboot.system.convert.SysMenuConvert;
import top.cadecode.uniboot.system.service.SysMenuService;
import top.cadecode.uniboot.system.service.SysRoleService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

import static top.cadecode.uniboot.system.request.SysMenuRequest.*;

/**
 * 菜单管理API
 *
 * @author Cade Li
 * @since 2023/4/12
 */
@ApiFormat
@Slf4j
@RequiredArgsConstructor
@Api(tags = "菜单管理")
@RequestMapping("system/menu")
@RestController
@Validated
public class SysMenuController {

    private final SysMenuService sysMenuService;
    private final SysRoleService sysRoleService;

    @ApiOperation("查询用户列表（带角色）")
    @PostMapping("page_roles_vo")
    public PageResult<SysMenuRolesVo> pageRolesVo(@RequestBody @Valid SysMenuRolesRequest request) {
        PageInfo<SysMenuRolesVo> rolesVoPage = sysMenuService.pageRolesVo(request);
        return new PageResult<>((int) rolesVoPage.getTotal(), rolesVoPage.getList());
    }

    @ApiOperation("更新用户启用状态")
    @PostMapping("update_enable")
    public boolean updateEnable(@RequestBody @Valid SysMenuUpdateEnableRequest request) {
        return sysMenuService.lambdaUpdate()
                .eq(SysMenu::getId, request.getId())
                .set(SysMenu::getEnableFlag, request.getEnableFlag())
                .update(new SysMenu());
    }

    @ApiOperation("更新用户")
    @PostMapping("update")
    public boolean update(@RequestBody @Valid SysMenuUpdateRequest request) {
        SysMenu sysMenu = SysMenuConvert.INSTANCE.requestToPo(request);
        return sysMenuService.updateById(sysMenu);
    }

    @ApiOperation("添加用户")
    @PostMapping("add")
    public boolean add(@RequestBody @Valid SysMenuAddRequest request) {
        SysMenu sysMenu = SysMenuConvert.INSTANCE.requestToPo(request);
        return sysMenuService.save(sysMenu);
    }

    @ApiOperation("删除用户（多选）")
    @PostMapping("delete")
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(@RequestBody @NotEmpty List<Long> menuIdList) {
        // 清理菜单角色绑定关系
        sysRoleService.removeRoleMenuByMenuIds(menuIdList);
        return sysMenuService.removeBatchByIds(menuIdList);
    }

    @ApiOperation("获取用户（带角色）byMenuIds")
    @PostMapping("list_roles_vo_by_menu_ids")
    public List<SysMenuRolesVo> listRolesVoByMenuIds(@RequestBody @NotEmpty List<Long> menuIdList) {
        return sysMenuService.listRolesVoByMenuIds(menuIdList);
    }
}
