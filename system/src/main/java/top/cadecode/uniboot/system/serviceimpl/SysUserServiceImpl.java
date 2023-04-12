package top.cadecode.uniboot.system.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import top.cadecode.uniboot.common.exception.UniException;
import top.cadecode.uniboot.system.bean.dto.SysUserDto.SysUserDetailsDto;
import top.cadecode.uniboot.system.bean.po.SysRole;
import top.cadecode.uniboot.system.bean.po.SysUser;
import top.cadecode.uniboot.system.convert.SysUserConvert;
import top.cadecode.uniboot.system.mapper.SysRoleMapper;
import top.cadecode.uniboot.system.mapper.SysUserMapper;
import top.cadecode.uniboot.system.service.SysUserService;

import java.util.List;
import java.util.Optional;
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

    private final SysRoleMapper sysRoleMapper;

    /**
     * UserDetailsService 实现
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<SysUser> sysUserOpt = lambdaQuery().eq(SysUser::getUsername, username).oneOpt();
        // 用户账户不存在
        sysUserOpt.orElseThrow(() -> UniException.of("该用户不存在"));
        // 用户账号被关闭
        if (!sysUserOpt.get().isEnableFlag()) {
            throw UniException.of("账号已被关闭");
        }
        SysUserDetailsDto sysUserDetailsDto = SysUserConvert.INSTANCE.poToDetailsDto(sysUserOpt.get());
        List<SysRole> sysRoles = sysRoleMapper.listByUserId(sysUserDetailsDto.getId());
        sysUserDetailsDto.setRoles(sysRoles.stream().map(SysRole::getCode).collect(Collectors.toList()));
        return sysUserDetailsDto;
    }
}
