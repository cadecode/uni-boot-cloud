package top.cadecode.uniboot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.cadecode.uniboot.system.bean.po.SysRole;

import java.util.List;

/**
 * 系统角色 DAO
 *
 * @author Cade Li
 * @date 2022/5/27
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> listByUserIds(@Param("userIds") List<Long> userIds);

    List<SysRole> listByMenuIds(@Param("menuIds") List<Long> menuIds);

    List<SysRole> listByApiIds(@Param("apiIds") List<Long> apiIds);

    int deleteRoleUserByUserIds(@Param("userIds") List<Long> userIds);

    int deleteRoleUserByRoleIds(@Param("roleIds") List<Long> roleIds);

    int deleteRoleMenuByMenuIds(@Param("menuIds") List<Long> menuIds);

    int deleteRoleMenuByRoleIds(@Param("roleIds") List<Long> roleIds);

    int deleteRoleApiByApiIds(@Param("apiIds") List<Long> apiIds);

    int deleteRoleApiByRoleIds(@Param("roleIds") List<Long> roleIds);

}
