package top.cadecode.uniboot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.cadecode.uniboot.system.bean.po.SysUser;

/**
 * 系统用户 DAO
 *
 * @author Cade Li
 * @date 2022/5/27
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
