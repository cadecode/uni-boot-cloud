package top.cadecode.uniboot.system.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.cadecode.uniboot.system.bean.po.SysMenu;
import top.cadecode.uniboot.system.bean.vo.SysMenuVo.SysMenuTreeVo;

/**
 * 系统菜单BEAN转换
 *
 * @author Cade Li
 * @date 2022/5/27
 */
@Mapper
public interface SysMenuConvert {

    SysMenuConvert INSTANCE = Mappers.getMapper(SysMenuConvert.class);

    SysMenuTreeVo toTreeVo(SysMenu sysMenu);

}
