package com.github.cadecode.uniboot.framework.svc.convert;

import com.github.cadecode.uniboot.framework.svc.bean.po.SysDept;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysDeptVo.SysDeptAddReqVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysDeptVo.SysDeptQueryResVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysDeptVo.SysDeptTreeResVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysDeptVo.SysDeptUpdateReqVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 部门 bean convert
 *
 * @author Cade Li
 * @since 2023/11/24
 */
@Mapper
public interface SysDeptConvert {

    SysDeptConvert INSTANCE = Mappers.getMapper(SysDeptConvert.class);

    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    SysDept voToPo(SysDeptAddReqVo reqVo);

    @Mapping(target = "parentId", ignore = true)
    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    SysDept voToPo(SysDeptUpdateReqVo reqVo);

    List<SysDeptQueryResVo> poToQueryResVo(List<SysDept> poList);

    List<SysDeptTreeResVo> poToTreeResVo(List<SysDept> poList);
}
