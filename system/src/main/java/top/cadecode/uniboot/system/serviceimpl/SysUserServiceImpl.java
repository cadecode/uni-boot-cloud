package top.cadecode.uniboot.system.serviceimpl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import top.cadecode.uniboot.common.exception.UniException;
import top.cadecode.uniboot.common.util.JacksonUtil;
import top.cadecode.uniboot.system.bean.po.SysUser;
import top.cadecode.uniboot.system.bean.vo.SysUserVo.SysUserRolesVo;
import top.cadecode.uniboot.system.convert.SysUserConvert;
import top.cadecode.uniboot.system.mapper.SysUserMapper;
import top.cadecode.uniboot.system.request.SysUserRequest.SysUserRolesRequest;
import top.cadecode.uniboot.system.service.SysUserService;

import java.util.List;
import java.util.stream.Collectors;

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
            throw UniException.of("该用户不存在");
        }
        SysUserRolesVo userRolesVo = userRolesVoList.get(0);
        // 用户账号被关闭
        if (!userRolesVo.getEnableFlag()) {
            throw UniException.of("账号已被关闭");
        }
        return SysUserConvert.INSTANCE.voToDetailsDto(userRolesVo);
    }

    @Override
    public List<SysUserRolesVo> listRolesVoByUserIds(List<Long> userIds) {
        // 通过rolesJson转换
        return sysUserMapper.selectRolesVoByUserIds(userIds).stream()
                .peek(o -> o.setRoles(JacksonUtil.toBean(o.getRolesJson(), new TypeReference<List<String>>() {})))
                .collect(Collectors.toList());
    }

    @Override
    public List<SysUserRolesVo> listRolesVo(SysUserRolesRequest request) {
        return sysUserMapper.selectRolesVo(request).stream()
                .peek(o -> o.setRoles(JacksonUtil.toBean(o.getRolesJson(), new TypeReference<List<String>>() {})))
                .collect(Collectors.toList());
    }

    @Override
    public PageInfo<SysUserRolesVo> pageRolesVo(SysUserRolesRequest request) {
        return PageHelper.startPage(request.getPageNumber(), request.getPageSize())
                .doSelectPageInfo(() -> listRolesVo(request));
    }
}
