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
import top.cadecode.uniboot.framework.bean.po.SysMenu;
import top.cadecode.uniboot.framework.bean.vo.SysMenuVo.SysMenuRolesVo;
import top.cadecode.uniboot.framework.convert.SysMenuConvert;
import top.cadecode.uniboot.framework.service.SysMenuService;
import top.cadecode.uniboot.framework.service.SysRoleService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static top.cadecode.uniboot.framework.request.SysMenuRequest.*;

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

    @ApiOperation("查询菜单列表（带角色）")
    @PostMapping("page_roles_vo")
    public PageResult<SysMenuRolesVo> pageRolesVo(@RequestBody @Valid SysMenuRolesRequest request) {
        PageInfo<SysMenuRolesVo> rolesVoPage = sysMenuService.pageRolesVo(request);
        return new PageResult<>((int) rolesVoPage.getTotal(), rolesVoPage.getList());
    }

    @ApiOperation("更新菜单启用状态")
    @PostMapping("update_enable")
    public boolean updateEnable(@RequestBody @Valid SysMenuUpdateEnableRequest request) {
        return sysMenuService.updateById(SysMenu.builder()
                .id(request.getId())
                .enableFlag(request.getEnableFlag())
                .build());
    }

    @ApiOperation("更新菜单")
    @PostMapping("update")
    public boolean update(@RequestBody @Valid SysMenuUpdateRequest request) {
        SysMenu sysMenu = SysMenuConvert.INSTANCE.requestToPo(request);
        return sysMenuService.updateById(sysMenu);
    }

    @ApiOperation("添加菜单")
    @PostMapping("add")
    public boolean add(@RequestBody @Valid SysMenuAddRequest request) {
        SysMenu sysMenu = SysMenuConvert.INSTANCE.requestToPo(request);
        return sysMenuService.save(sysMenu);
    }

    @ApiOperation("删除菜单（多选）")
    @PostMapping("delete")
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(@RequestBody @NotEmpty List<Long> menuIdList) {
        List<Long> assumedParentList = new ArrayList<>(menuIdList);
        while (!assumedParentList.isEmpty()) {
            List<SysMenu> children = sysMenuService.lambdaQuery()
                    .select(SysMenu::getId, SysMenu::getLeafFlag).in(SysMenu::getParentId, assumedParentList)
                    .list();
            // 加入删除名单
            menuIdList.addAll(children.stream().map(SysMenu::getId).collect(Collectors.toList()));
            // 非页面的菜单 id 再次查询
            assumedParentList = children.stream()
                    .filter(o -> !o.getLeafFlag())
                    .map(SysMenu::getId)
                    .collect(Collectors.toList());
        }
        // 清理菜单角色绑定关系
        sysRoleService.removeRoleMenuByMenuIds(menuIdList);
        return sysMenuService.removeBatchByIds(menuIdList);
    }

    @ApiOperation("获取菜单（带角色）byMenuIds")
    @PostMapping("list_roles_vo_by_menu_ids")
    public List<SysMenuRolesVo> listRolesVoByMenuIds(@RequestBody @NotEmpty List<Long> menuIdList) {
        return sysMenuService.listRolesVoByMenuIds(menuIdList);
    }
}
