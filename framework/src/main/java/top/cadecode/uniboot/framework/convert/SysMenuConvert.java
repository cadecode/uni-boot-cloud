package top.cadecode.uniboot.framework.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.cadecode.uniboot.framework.bean.po.SysMenu;
import top.cadecode.uniboot.framework.bean.vo.SysMenuVo.SysMenuTreeVo;
import top.cadecode.uniboot.framework.request.SysMenuRequest.SysMenuAddRequest;
import top.cadecode.uniboot.framework.request.SysMenuRequest.SysMenuUpdateRequest;

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

    SysMenu requestToPo(SysMenuUpdateRequest request);

    SysMenu requestToPo(SysMenuAddRequest request);

}
