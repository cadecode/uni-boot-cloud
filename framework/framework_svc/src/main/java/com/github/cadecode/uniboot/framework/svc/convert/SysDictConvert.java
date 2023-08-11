package com.github.cadecode.uniboot.framework.svc.convert;

import com.github.cadecode.uniboot.framework.svc.bean.po.SysDict;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysDictVo.SysDictGetByTypeVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysDictVo.SysDictPageVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysDictVo.SysDictSuggestVo;
import com.github.cadecode.uniboot.framework.svc.request.SysDictRequest.SysDictAddRequest;
import com.github.cadecode.uniboot.framework.svc.request.SysDictRequest.SysDictUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统字典 Bean 转换
 *
 * @author Cade Li
 * @date 2023/6/11
 */
@Mapper
public interface SysDictConvert {
    SysDictConvert INSTANCE = Mappers.getMapper(SysDictConvert.class);

    List<SysDictPageVo> poToPageVo(List<SysDict> records);

    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    SysDict requestToPo(SysDictAddRequest request);

    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    SysDict requestToPo(SysDictUpdateRequest request);

    List<SysDictGetByTypeVo> poToGetByTypeVo(List<SysDict> dictList);

    SysDictSuggestVo poToSuggestVo(SysDict po);
}
