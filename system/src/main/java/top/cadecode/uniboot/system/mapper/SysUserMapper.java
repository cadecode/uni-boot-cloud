package top.cadecode.uniboot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.cadecode.uniboot.system.bean.po.SysUser;
import top.cadecode.uniboot.system.bean.vo.SysUserVo.SysUserRolesVo;

import java.util.List;

/**
 * 系统用户 DAO
 *
 * @author Cade Li
 * @date 2022/5/27
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<SysUserRolesVo> listRolesVo(@Param("userIds") List<Long> userIds);

    List<SysUserRolesVo> listRolesVoByUsername(@Param("username") String username);
}
