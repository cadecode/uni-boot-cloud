package com.github.cadecode.uniboot.framework.svc.convert;

import com.github.cadecode.uniboot.framework.svc.bean.po.SysMenu;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysMenuVo.SysMenuTreeVo;
import com.github.cadecode.uniboot.framework.svc.request.SysMenuRequest.SysMenuAddRequest;
import com.github.cadecode.uniboot.framework.svc.request.SysMenuRequest.SysMenuUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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

    @Mapping(target = "children", ignore = true)
    SysMenuTreeVo toTreeVo(SysMenu sysMenu);

    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "leafFlag", ignore = true)
    @Mapping(target = "enableFlag", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    SysMenu requestToPo(SysMenuUpdateRequest request);

    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    SysMenu requestToPo(SysMenuAddRequest request);

}
