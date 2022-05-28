package top.cadecode.sra.system.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.cadecode.sra.system.bean.po.SysRole;
import top.cadecode.sra.system.mapper.SysRoleMapper;
import top.cadecode.sra.system.service.SysRoleService;

/**
 * @author Cade Li
 * @date 2022/5/27
 * @description 系统角色服务实现
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
}
