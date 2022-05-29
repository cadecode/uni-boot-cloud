package top.cadecode.sra.system.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import top.cadecode.sra.common.exception.ApiException;
import top.cadecode.sra.system.bean.dto.SysUserDto;
import top.cadecode.sra.system.bean.po.SysRole;
import top.cadecode.sra.system.bean.po.SysUser;
import top.cadecode.sra.system.convert.SysUserConvert;
import top.cadecode.sra.system.mapper.SysRoleMapper;
import top.cadecode.sra.system.mapper.SysUserMapper;
import top.cadecode.sra.system.service.SysUserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Cade Li
 * @date 2022/5/27
 * @description 系统用户服务实现
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
        sysUserOpt.orElseThrow(() -> ApiException.of("该用户不存在"));
        // 用户账号被关闭
        if (!sysUserOpt.get().isEnableFlag()) {
            throw ApiException.of("账号已被关闭");
        }
        SysUserDto sysUserDto = SysUserConvert.INSTANCE.poToDto(sysUserOpt.get());
        List<SysRole> sysRoles = sysRoleMapper.listByUserId(sysUserDto.getId());
        sysUserDto.setRoles(sysRoles.stream().map(SysRole::getCode).collect(Collectors.toList()));
        return sysUserDto;
    }
}
