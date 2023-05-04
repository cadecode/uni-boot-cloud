package top.cadecode.uniboot.system.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.cadecode.uniboot.system.bean.po.SysRole;
import top.cadecode.uniboot.system.mapper.SysRoleMapper;
import top.cadecode.uniboot.system.service.SysRoleService;

import java.util.List;

/**
 * 系统角色服务实现
 *
 * @author Cade Li
 * @date 2022/5/27
 */
@RequiredArgsConstructor
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRoleMapper sysRoleMapper;

    @Override
    public List<SysRole> listByUserIds(List<Long> userIds) {
        return sysRoleMapper.listByUserIds(userIds);
    }

    @Override
    public List<SysRole> listByMenuIds(List<Long> menuIds) {
        return sysRoleMapper.listByMenuIds(menuIds);
    }

    @Override
    public List<SysRole> listByApiIds(List<Long> apiIds) {
        return sysRoleMapper.listByApiIds(apiIds);
    }

    @Override
    public int deleteRoleUserByUserIds(List<Long> userIds) {
        return sysRoleMapper.deleteRoleUserByUserIds(userIds);
    }

    @Override
    public int deleteRoleUserByRoleIds(List<Long> roleIds) {
        return sysRoleMapper.deleteRoleUserByRoleIds(roleIds);
    }

    @Override
    public int deleteRoleMenuByMenuIds(List<Long> menuIds) {
        return sysRoleMapper.deleteRoleMenuByMenuIds(menuIds);
    }

    @Override
    public int deleteRoleMenuByRoleIds(List<Long> roleIds) {
        return sysRoleMapper.deleteRoleMenuByRoleIds(roleIds);

    }

    @Override
    public int deleteRoleApiByApiIds(List<Long> apiIds) {
        return sysRoleMapper.deleteRoleApiByApiIds(apiIds);
    }

    @Override
    public int deleteRoleApiByRoleIds(List<Long> roleIds) {
        return sysRoleMapper.deleteRoleApiByRoleIds(roleIds);
    }
}
