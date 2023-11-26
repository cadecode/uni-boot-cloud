package com.github.cadecode.uniboot.framework.svc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.cadecode.uniboot.framework.svc.bean.po.SysDept;
import org.apache.ibatis.annotations.Mapper;

/**
 * 部门 DAO
 *
 * @author Cade Li
 * @since 2023/11/24
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {
}
