package com.github.cadecode.uniboot.framework.svc.convert;

import com.github.cadecode.uniboot.framework.svc.bean.po.SysApi;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysApiVo.SysApiAddReqVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysApiVo.SysApiUpdateReqVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 系统接口 BEAN 转换
 *
 * @author Cade Li
 * @date 2022/5/27
 */
@Mapper
public interface SysApiConvert {

    SysApiConvert INSTANCE = Mappers.getMapper(SysApiConvert.class);

    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    SysApi voToPo(SysApiUpdateReqVo reqVo);

    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    SysApi voToPo(SysApiAddReqVo reqVo);

}
