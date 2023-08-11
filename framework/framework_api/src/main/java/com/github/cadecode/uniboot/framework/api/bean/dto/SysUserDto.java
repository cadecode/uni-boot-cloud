package com.github.cadecode.uniboot.framework.api.bean.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统用户 DTO，实现 UserDetails 接口
 *
 * @author Cade Li
 * @date 2022/5/24
 */
public class SysUserDto {

    /**
     * SpringSecurity UserDetails实现
     */
    @Data
    public static class SysUserDetailsDto implements UserDetails {

        private Long id;

        private String username;

        @JsonIgnore
        private String password;

        private String nickName;

        private Boolean enableFlag;

        private String sex;

        private String mail;

        private String phone;

        private String loginIp;

        private Date loginDate;

        List<String> roles;

        @JsonIgnore
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            // 重写获取权限的方法，从角色字符串创建 SimpleGrantedAuthority
            return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        }

        @JsonIgnore
        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @JsonIgnore
        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @JsonIgnore
        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @JsonIgnore
        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
