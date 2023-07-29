package com.github.cadecode.uniboot.framework.svc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.cadecode.uniboot.framework.api.bean.po.SysDict;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统字典 DAO
 *
 * @author Cade Li
 * @since 2023/5/26
 */
@Mapper
public interface SysDictMapper extends BaseMapper<SysDict> {
}
