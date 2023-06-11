package top.cadecode.uniboot.framework.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.cadecode.uniboot.framework.bean.po.SysDict;
import top.cadecode.uniboot.framework.bean.vo.SysDictVo.SysDictGetByTypeVo;
import top.cadecode.uniboot.framework.bean.vo.SysDictVo.SysDictPageVo;
import top.cadecode.uniboot.framework.bean.vo.SysDictVo.SysDictSuggestVo;
import top.cadecode.uniboot.framework.request.SysDictRequest.SysDictAddRequest;
import top.cadecode.uniboot.framework.request.SysDictRequest.SysDictUpdateRequest;

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
