package com.github.cadecode.uniboot.framework.base.plugin.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.cadecode.uniboot.framework.base.plugin.bean.po.PlgFile;
import com.github.cadecode.uniboot.framework.base.plugin.mapper.PlgFileMapper;
import com.github.cadecode.uniboot.framework.base.plugin.service.PlgFileService;
import org.springframework.stereotype.Service;

/**
 * PlgFile ServiceImpl
 *
 * @author Cade Li
 * @since 2023/10/25
 */
@Service
public class PlgFileServiceImpl extends ServiceImpl<PlgFileMapper, PlgFile> implements PlgFileService {
}
