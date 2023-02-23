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

    List<SysRole> listByUserId(@Param("userId") Long userId);

}
