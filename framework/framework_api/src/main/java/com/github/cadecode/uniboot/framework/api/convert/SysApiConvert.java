package com.github.cadecode.uniboot.framework.api.convert;

import com.github.cadecode.uniboot.framework.api.bean.po.SysApi;
import com.github.cadecode.uniboot.framework.api.bean.vo.SysApiVo.SysApiRolesVo;
import com.github.cadecode.uniboot.framework.api.request.SysApiRequest.SysApiAddRequest;
import com.github.cadecode.uniboot.framework.api.request.SysApiRequest.SysApiUpdateRequest;
import org.mapstruct.Mapper;
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

    SysApiRolesVo poToRolesVo(SysApi po);

    SysApi requestToPo(SysApiUpdateRequest request);

    SysApi requestToPo(SysApiAddRequest request);

}
