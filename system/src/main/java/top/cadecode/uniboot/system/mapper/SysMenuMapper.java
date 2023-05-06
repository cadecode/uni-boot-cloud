package top.cadecode.uniboot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.cadecode.uniboot.system.bean.po.SysMenu;
import top.cadecode.uniboot.system.bean.vo.SysMenuVo.SysMenuRolesVo;
import top.cadecode.uniboot.system.request.SysMenuRequest.SysMenuRolesRequest;

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
