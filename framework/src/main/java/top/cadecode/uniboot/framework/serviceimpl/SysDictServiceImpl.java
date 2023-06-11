package top.cadecode.uniboot.framework.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.cadecode.uniboot.framework.bean.po.SysDict;
import top.cadecode.uniboot.framework.mapper.SysDictMapper;
import top.cadecode.uniboot.framework.service.SysDictService;

/**
 * 系统字典服务实现
 *
 * @author Cade Li
 * @since 2023/5/26
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {
}
