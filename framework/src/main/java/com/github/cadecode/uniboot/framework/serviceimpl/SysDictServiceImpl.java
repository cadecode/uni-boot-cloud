package com.github.cadecode.uniboot.framework.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.cadecode.uniboot.framework.bean.po.SysDict;
import com.github.cadecode.uniboot.framework.mapper.SysDictMapper;
import com.github.cadecode.uniboot.framework.service.SysDictService;
import org.springframework.stereotype.Service;

/**
 * 系统字典服务实现
 *
 * @author Cade Li
 * @since 2023/5/26
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {
}
