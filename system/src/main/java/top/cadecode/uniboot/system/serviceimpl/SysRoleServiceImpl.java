package top.cadecode.uniboot.system.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.cadecode.uniboot.system.bean.po.SysRole;
import top.cadecode.uniboot.system.bean.vo.SysRoleVo.SysRoleUnionVo;
import top.cadecode.uniboot.system.mapper.SysRoleMapper;
import top.cadecode.uniboot.system.request.SysRoleRequest.SysRoleMappingRequest;
import top.cadecode.uniboot.system.request.SysRoleRequest.SysRoleUnionRequest;
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
        return sysRoleMapper.selectByUserIds(userIds);
    }

    @Override
    public List<SysRole> listByMenuIds(List<Long> menuIds) {
        return sysRoleMapper.selectByMenuIds(menuIds);
    }

    @Override
    public List<SysRole> listByApiIds(List<Long> apiIds) {
        return sysRoleMapper.selectByApiIds(apiIds);
    }

    @Override
    public int removeRoleUserByUserIds(List<Long> userIds) {
        return sysRoleMapper.deleteRoleUserByUserIds(userIds);
    }

    @Override
    public int removeRoleUserByRoleIds(List<Long> roleIds) {
        return sysRoleMapper.deleteRoleUserByRoleIds(roleIds);
    }

    @Override
    public int removeRoleMenuByMenuIds(List<Long> menuIds) {
        return sysRoleMapper.deleteRoleMenuByMenuIds(menuIds);
    }

    @Override
    public int removeRoleMenuByRoleIds(List<Long> roleIds) {
        return sysRoleMapper.deleteRoleMenuByRoleIds(roleIds);
    }

    @Override
    public int removeRoleApiByApiIds(List<Long> apiIds) {
        return sysRoleMapper.deleteRoleApiByApiIds(apiIds);
    }

    @Override
    public int removeRoleApiByRoleIds(List<Long> roleIds) {
        return sysRoleMapper.deleteRoleApiByRoleIds(roleIds);
    }

    @Override
    public int removeRoleUser(List<SysRoleMappingRequest> list) {
        return sysRoleMapper.deleteRoleUser(list);
    }

    @Override
    public int removeRoleMenu(List<SysRoleMappingRequest> list) {
        return sysRoleMapper.deleteRoleMenu(list);
    }

    @Override
    public int removeRoleApi(List<SysRoleMappingRequest> list) {
        return sysRoleMapper.deleteRoleApi(list);
    }

    @Override
    public int addRoleUser(List<SysRoleMappingRequest> list) {
        return sysRoleMapper.insertRoleUser(list);
    }

    @Override
    public int addRoleMenu(List<SysRoleMappingRequest> list) {
        return sysRoleMapper.insertRoleMenu(list);
    }

    @Override
    public int addRoleApi(List<SysRoleMappingRequest> list) {
        return sysRoleMapper.insertRoleApi(list);
    }

    @Override
    public List<SysRoleUnionVo> listUnionVo(SysRoleUnionRequest request) {
        return sysRoleMapper.selectRolesVo(request);
    }

    @Override
    public PageInfo<SysRoleUnionVo> pageUnionVo(SysRoleUnionRequest request) {
        return PageHelper.startPage(request.getPageNumber(), request.getPageSize())
                .doSelectPageInfo(() -> listUnionVo(request));
    }

    @Override
    public List<SysRoleUnionVo> listUnionVoByRoleIds(List<Long> roleIds) {
        return sysRoleMapper.selectRolesVoByRoleIds(roleIds);
    }
}
