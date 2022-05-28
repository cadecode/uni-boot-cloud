package top.cadecode.sra.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.cadecode.sra.system.bean.po.SysUser;

/**
 * @author Cade Li
 * @date 2022/5/27
 * @description 系统用户 DAO
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
