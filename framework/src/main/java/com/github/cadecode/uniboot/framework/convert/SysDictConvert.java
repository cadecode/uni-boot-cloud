package com.github.cadecode.uniboot.framework.convert;

import com.github.cadecode.uniboot.framework.bean.po.SysDict;
import com.github.cadecode.uniboot.framework.bean.vo.SysDictVo.SysDictGetByTypeVo;
import com.github.cadecode.uniboot.framework.bean.vo.SysDictVo.SysDictPageVo;
import com.github.cadecode.uniboot.framework.bean.vo.SysDictVo.SysDictSuggestVo;
import com.github.cadecode.uniboot.framework.request.SysDictRequest.SysDictAddRequest;
import com.github.cadecode.uniboot.framework.request.SysDictRequest.SysDictUpdateRequest;
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
