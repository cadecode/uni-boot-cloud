package com.github.cadecode.uniboot.framework.serviceimpl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.cadecode.uniboot.common.core.exception.ApiException;
import com.github.cadecode.uniboot.framework.bean.po.SysUser;
import com.github.cadecode.uniboot.framework.bean.vo.SysUserVo.SysUserRolesVo;
import com.github.cadecode.uniboot.framework.convert.SysUserConvert;
import com.github.cadecode.uniboot.framework.mapper.SysUserMapper;
import com.github.cadecode.uniboot.framework.request.SysUserRequest.SysUserRolesRequest;
import com.github.cadecode.uniboot.framework.service.SysUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统用户服务实现
 *
 * @author Cade Li
 * @date 2022/5/27
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserMapper sysUserMapper;

    /**
     * UserDetailsService 实现
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        List<SysUserRolesVo> userRolesVoList = sysUserMapper.selectRolesVoByUsername(username);
        // 用户账户不存在
        if (ObjectUtil.isEmpty(userRolesVoList)) {
            throw ApiException.of("该用户不存在");
        }
        SysUserRolesVo userRolesVo = userRolesVoList.get(0);
        // 用户账号被关闭
        if (!userRolesVo.getEnableFlag()) {
            throw ApiException.of("账号已被关闭");
        }
        return SysUserConvert.INSTANCE.voToDetailsDto(userRolesVo);
    }

    @Override
    public List<SysUserRolesVo> listRolesVoByUserIds(List<Long> userIds) {
        return sysUserMapper.selectRolesVoByUserIds(userIds);
    }

    @Override
    public List<SysUserRolesVo> listRolesVo(SysUserRolesRequest request) {
        return sysUserMapper.selectRolesVo(request);
    }

    @Override
    public PageInfo<SysUserRolesVo> pageRolesVo(SysUserRolesRequest request) {
        return PageHelper.startPage(request.getPageNumber(), request.getPageSize())
                .doSelectPageInfo(() -> listRolesVo(request));
    }
}
