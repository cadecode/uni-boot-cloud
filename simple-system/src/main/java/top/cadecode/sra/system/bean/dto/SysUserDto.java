package top.cadecode.sra.system.bean.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Cade Li
 * @date 2022/5/24
 * @description 系统用户 DTO，实现 UserDetails 接口
 */
@Data
public class SysUserDto implements UserDetails {

    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 是否启用
     */
    private Boolean enableFlag;

    /**
     * 角色
     */
    List<String> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 重写获取权限的方法，从角色字符串创建 SimpleGrantedAuthority
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
