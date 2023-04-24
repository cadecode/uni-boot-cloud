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
    public List<SysRole> listByUserId(Long userId) {
        return sysRoleMapper.listByUserId(userId);
    }

    @Override
    public List<SysRole> listByMenuId(Long menuId) {
        return sysRoleMapper.listByMenuId(menuId);
    }

    @Override
    public List<SysRole> listByApiId(Long apiId) {
        return sysRoleMapper.listByApiId(apiId);
    }
}
