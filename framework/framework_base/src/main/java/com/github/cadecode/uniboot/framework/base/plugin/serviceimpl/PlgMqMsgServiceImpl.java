package com.github.cadecode.uniboot.framework.base.plugin.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.cadecode.uniboot.framework.base.plugin.bean.po.PlgMqMsg;
import com.github.cadecode.uniboot.framework.base.plugin.mapper.PlgMqMsgMapper;
import com.github.cadecode.uniboot.framework.base.plugin.service.PlgMqMsgService;
import org.springframework.stereotype.Service;

/**
 * PlgMqMsg Service 实现
 *
 * @author Cade Li
 * @since 2023/5/26
 */
@Service
public class PlgMqMsgServiceImpl extends ServiceImpl<PlgMqMsgMapper, PlgMqMsg> implements PlgMqMsgService {
}
