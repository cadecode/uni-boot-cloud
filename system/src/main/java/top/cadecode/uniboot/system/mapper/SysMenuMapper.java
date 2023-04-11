package top.cadecode.uniboot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.cadecode.uniboot.system.bean.po.SysMenu;

/**
 * 系统菜单DAO
 *
 * @author Cade Li
 * @date 2022/5/27
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
}
