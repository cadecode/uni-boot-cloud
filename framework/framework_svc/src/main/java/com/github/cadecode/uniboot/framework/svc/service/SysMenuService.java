package com.github.cadecode.uniboot.framework.svc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.cadecode.uniboot.framework.svc.bean.po.SysMenu;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysMenuVo.SysMenuRolesReqVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysMenuVo.SysMenuRolesResVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysMenuVo.SysMenuTreeResVo;
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

    List<SysMenuTreeResVo> listTreeVoByRoles(List<String> roleCodes);

    List<SysMenuRolesResVo> listRolesVo(SysMenuRolesReqVo reqVo);

    PageInfo<SysMenuRolesResVo> pageRolesVo(SysMenuRolesReqVo reqVo);

    List<SysMenuRolesResVo> listRolesVoByMenuIds(List<Long> menuIds);

}
