package com.github.cadecode.uniboot.framework.api.convert;

import com.github.cadecode.uniboot.framework.api.bean.po.SysDict;
import com.github.cadecode.uniboot.framework.api.bean.vo.SysDictVo.SysDictGetByTypeVo;
import com.github.cadecode.uniboot.framework.api.bean.vo.SysDictVo.SysDictPageVo;
import com.github.cadecode.uniboot.framework.api.bean.vo.SysDictVo.SysDictSuggestVo;
import com.github.cadecode.uniboot.framework.api.request.SysDictRequest.SysDictAddRequest;
import com.github.cadecode.uniboot.framework.api.request.SysDictRequest.SysDictUpdateRequest;
import org.mapstruct.Mapper;
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

    SysDict requestToPo(SysDictAddRequest request);

    SysDict requestToPo(SysDictUpdateRequest request);

    List<SysDictGetByTypeVo> poToGetByTypeVo(List<SysDict> dictList);

    SysDictSuggestVo poToSuggestVo(SysDict po);
}
