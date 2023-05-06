package top.cadecode.uniboot.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import top.cadecode.uniboot.system.bean.po.SysMenu;
import top.cadecode.uniboot.system.bean.vo.SysMenuVo.SysMenuRolesVo;
import top.cadecode.uniboot.system.bean.vo.SysMenuVo.SysMenuTreeVo;
import top.cadecode.uniboot.system.request.SysMenuRequest.SysMenuRolesRequest;

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
