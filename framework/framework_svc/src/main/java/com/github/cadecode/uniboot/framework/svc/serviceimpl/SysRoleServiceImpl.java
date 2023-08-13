package com.github.cadecode.uniboot.framework.svc.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.cadecode.uniboot.framework.api.consts.KeyPrefixConst;
import com.github.cadecode.uniboot.framework.svc.bean.po.SysRole;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysRoleVo.SysRoleMappingReqVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysRoleVo.SysRoleUnionReqVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysRoleVo.SysRoleUnionResVo;
import com.github.cadecode.uniboot.framework.svc.mapper.SysRoleMapper;
import com.github.cadecode.uniboot.framework.svc.service.SysRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

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

    @CacheEvict(cacheNames = KeyPrefixConst.API_ROLES, key = "'all'")
    @Override
    public int removeRoleApiByApiIds(List<Long> apiIds) {
        return sysRoleMapper.deleteRoleApiByApiIds(apiIds);
    }

    @CacheEvict(cacheNames = KeyPrefixConst.API_ROLES, key = "'all'")
    @Override
    public int removeRoleApiByRoleIds(List<Long> roleIds) {
        return sysRoleMapper.deleteRoleApiByRoleIds(roleIds);
    }

    @Override
    public int removeRoleUser(List<SysRoleMappingReqVo> list) {
        return sysRoleMapper.deleteRoleUser(list);
    }

    @Override
    public int removeRoleMenu(List<SysRoleMappingReqVo> list) {
        return sysRoleMapper.deleteRoleMenu(list);
    }

    @CacheEvict(cacheNames = KeyPrefixConst.API_ROLES, key = "'all'")
    @Override
    public int removeRoleApi(List<SysRoleMappingReqVo> list) {
        return sysRoleMapper.deleteRoleApi(list);
    }

    @Override
    public int addRoleUser(List<SysRoleMappingReqVo> list) {
        return sysRoleMapper.insertRoleUser(list);
    }

    @Override
    public int addRoleMenu(List<SysRoleMappingReqVo> list) {
        return sysRoleMapper.insertRoleMenu(list);
    }

    @CacheEvict(cacheNames = KeyPrefixConst.API_ROLES, key = "'all'")
    @Override
    public int addRoleApi(List<SysRoleMappingReqVo> list) {
        return sysRoleMapper.insertRoleApi(list);
    }

    @Override
    public List<SysRoleUnionResVo> listUnionVo(SysRoleUnionReqVo reqVo) {
        return sysRoleMapper.selectUnionVo(reqVo);
    }

    @Override
    public PageInfo<SysRoleUnionResVo> pageUnionVo(SysRoleUnionReqVo reqVo) {
        return PageHelper.startPage(reqVo.getPageNumber(), reqVo.getPageSize())
                .doSelectPageInfo(() -> listUnionVo(reqVo));
    }

    @Override
    public List<SysRoleUnionResVo> listUnionVoByRoleIds(List<Long> roleIds) {
        return sysRoleMapper.selectUnionVoByRoleIds(roleIds);
    }
}
