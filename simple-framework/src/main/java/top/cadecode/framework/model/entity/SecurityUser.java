package top.cadecode.framework.model.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author Cade Li
 * @date 2021/12/5
 * @description Spring Security 用户类
 */
@Data
public class SecurityUser implements UserDetails {
    // id
    private Long id;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 昵称
    private String nickName;
    // 是否启用
    private String enableFlag;
    // token
    private String token;
    // token 时间
    private String tokenTime;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
