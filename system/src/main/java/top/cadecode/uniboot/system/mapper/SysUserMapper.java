package top.cadecode.uniboot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.cadecode.uniboot.system.bean.po.SysUser;
import top.cadecode.uniboot.system.bean.vo.SysUserVo.SysUserRolesVo;
import top.cadecode.uniboot.system.request.SysUserRequest.SysUserRolesRequest;

import java.util.List;

/**
 * 系统用户 DAO
 *
 * @author Cade Li
 * @date 2022/5/27
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<SysUserRolesVo> selectRolesVoByUserIds(@Param("userIds") List<Long> userIds);

    List<SysUserRolesVo> selectRolesVoByUsername(@Param("username") String username);

    List<SysUserRolesVo> selectRolesVo(@Param("request") SysUserRolesRequest request);

}
