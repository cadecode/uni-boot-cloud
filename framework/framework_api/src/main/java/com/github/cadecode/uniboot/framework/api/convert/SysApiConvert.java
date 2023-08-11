package com.github.cadecode.uniboot.framework.api.convert;

import com.github.cadecode.uniboot.framework.api.bean.po.SysApi;
import com.github.cadecode.uniboot.framework.api.request.SysApiRequest.SysApiAddRequest;
import com.github.cadecode.uniboot.framework.api.request.SysApiRequest.SysApiUpdateRequest;
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
    SysApi requestToPo(SysApiUpdateRequest request);

    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    SysApi requestToPo(SysApiAddRequest request);

}
