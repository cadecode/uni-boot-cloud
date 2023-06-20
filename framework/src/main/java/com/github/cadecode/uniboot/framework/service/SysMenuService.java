package com.github.cadecode.uniboot.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.cadecode.uniboot.framework.bean.po.SysMenu;
import com.github.cadecode.uniboot.framework.bean.vo.SysMenuVo.SysMenuRolesVo;
import com.github.cadecode.uniboot.framework.bean.vo.SysMenuVo.SysMenuTreeVo;
import com.github.cadecode.uniboot.framework.request.SysMenuRequest.SysMenuRolesRequest;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 系统菜单服务
 *
 * @author Cade Li
 * @date 2022/5/27
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> listByRoles(List<String> roleCodes);

    List<SysMenuTreeVo> listTreeVoByRoles(List<String> roleCodes);

    List<SysMenuRolesVo> listRolesVo(SysMenuRolesRequest request);

    PageInfo<SysMenuRolesVo> pageRolesVo(SysMenuRolesRequest request);

    List<SysMenuRolesVo> listRolesVoByMenuIds(List<Long> menuIds);

}
