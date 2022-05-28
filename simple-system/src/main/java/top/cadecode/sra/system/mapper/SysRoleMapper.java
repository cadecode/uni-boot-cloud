package top.cadecode.sra.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.cadecode.sra.system.bean.po.SysRole;

/**
 * @author Cade Li
 * @date 2022/5/27
 * @description 系统角色 DAO
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
}
