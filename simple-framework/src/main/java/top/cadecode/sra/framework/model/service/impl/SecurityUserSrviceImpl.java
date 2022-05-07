package top.cadecode.sra.framework.model.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.cadecode.sra.common.core.exception.BaseException;
import top.cadecode.sra.common.enums.BaseErrorEnum;
import top.cadecode.sra.framework.model.entity.SecurityUser;
import top.cadecode.sra.framework.model.mapper.SecurityUserMapper;
import top.cadecode.sra.framework.model.service.SecurityUserSrvice;
import top.cadecode.sra.framework.model.vo.SecurityUserVo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Cade Li
 * @date 2021/12/11
 * @description Spring Security UserDetailsService 实现
 */
@Service
@RequiredArgsConstructor
public class SecurityUserSrviceImpl implements SecurityUserSrvice, UserDetailsService {

    private final SecurityUserMapper securityUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户 VO
        SecurityUserVo securityUserVo = securityUserMapper.getSecurityUserVo(username);
        // 没有查询到任何记录
        if (securityUserVo == null) {
            throw BaseException.of(BaseErrorEnum.TOKEN_CREATE_ERROR).errorMsg("用户名或密码错误");
        }
        // 用户账号被关闭
        if (!securityUserVo.isEnableFlag()) {
            throw BaseException.of(BaseErrorEnum.TOKEN_CREATE_ERROR).errorMsg("账号已被关闭");
        }
        // 创建 UserDetails，并设置属性
        SecurityUser securityUser = new SecurityUser();
        BeanUtils.copyProperties(securityUserVo, securityUser);
        // 转换 roles 字符串到 SimpleGrantedAuthority
        List<SimpleGrantedAuthority> authorities = securityUserVo.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
        // 给 UserDetails 设置权限
        securityUser.setAuthorities(authorities);
        return securityUser;
    }
}
