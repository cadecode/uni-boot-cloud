package top.cadecode.uniboot.system.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.cadecode.uniboot.system.bean.po.SysRole;
import top.cadecode.uniboot.system.mapper.SysRoleMapper;
import top.cadecode.uniboot.system.service.SysRoleService;

/**
 * 系统角色服务实现
 *
 * @author Cade Li
 * @date 2022/5/27
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
}
