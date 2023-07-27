package com.github.cadecode.uniboot.framework.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.cadecode.uniboot.framework.api.bean.po.SysMenu;
import com.github.cadecode.uniboot.framework.api.bean.vo.SysMenuVo.SysMenuRolesVo;
import com.github.cadecode.uniboot.framework.api.request.SysMenuRequest.SysMenuRolesRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统菜单DAO
 *
 * @author Cade Li
 * @date 2022/5/27
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> selectByRoles(@Param("roleCodes") List<String> roleCodes);

    List<SysMenuRolesVo> selectRolesVo(@Param("request") SysMenuRolesRequest request);

    List<SysMenuRolesVo> selectRolesVoByMenuIds(@Param("menuIds") List<Long> menuIds);

}
