package com.github.cadecode.uniboot.framework.api.convert;

import com.github.cadecode.uniboot.framework.api.bean.po.SysMenu;
import com.github.cadecode.uniboot.framework.api.bean.vo.SysMenuVo.SysMenuTreeVo;
import com.github.cadecode.uniboot.framework.api.request.SysMenuRequest.SysMenuAddRequest;
import com.github.cadecode.uniboot.framework.api.request.SysMenuRequest.SysMenuUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

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
