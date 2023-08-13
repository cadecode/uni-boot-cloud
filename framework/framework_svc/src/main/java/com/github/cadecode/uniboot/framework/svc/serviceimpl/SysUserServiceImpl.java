package com.github.cadecode.uniboot.framework.svc.serviceimpl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.cadecode.uniboot.common.core.exception.ApiException;
import com.github.cadecode.uniboot.framework.svc.bean.po.SysUser;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysUserVo.SysUserRolesReqVo;
import com.github.cadecode.uniboot.framework.svc.bean.vo.SysUserVo.SysUserRolesResVo;
import com.github.cadecode.uniboot.framework.svc.convert.SysUserConvert;
import com.github.cadecode.uniboot.framework.svc.mapper.SysUserMapper;
import com.github.cadecode.uniboot.framework.svc.service.SysUserService;
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
        List<SysUserRolesResVo> userRolesVoList = sysUserMapper.selectRolesVoByUsername(username);
        // 用户账户不存在
        if (ObjectUtil.isEmpty(userRolesVoList)) {
            throw ApiException.of("该用户不存在");
        }
        SysUserRolesResVo userRolesVo = userRolesVoList.get(0);
        // 用户账号被关闭
        if (!userRolesVo.getEnableFlag()) {
            throw ApiException.of("账号已被关闭");
        }
        return SysUserConvert.INSTANCE.voToSysUserDetails(userRolesVo);
    }

    @Override
    public List<SysUserRolesResVo> listRolesVoByUserIds(List<Long> userIds) {
        return sysUserMapper.selectRolesVoByUserIds(userIds);
    }

    @Override
    public List<SysUserRolesResVo> listRolesVo(SysUserRolesReqVo reqVo) {
        return sysUserMapper.selectRolesVo(reqVo);
    }

    @Override
    public PageInfo<SysUserRolesResVo> pageRolesVo(SysUserRolesReqVo reqVo) {
        return PageHelper.startPage(reqVo.getPageNumber(), reqVo.getPageSize())
                .doSelectPageInfo(() -> listRolesVo(reqVo));
    }
}
