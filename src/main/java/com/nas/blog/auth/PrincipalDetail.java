package com.nas.blog.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nas.blog.entity.User;
import com.nas.blog.user.model.RoleType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class PrincipalDetail implements UserDetails {
    private Long id;
    private String email;
    private String password;
    private String username;
    private RoleType role;

    public PrincipalDetail(User user) {
        id = user.getId();
        email = user.getEmail();
        password = user.getPassword();
        username = user.getUserName();
        role = user.getRole();
    }

    public Long getId() {
        return id;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(() -> {
            return RoleType.ROLE_USER.toString();
        });
        return collectors;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    //계정이 만료되지 않았는지 리턴 (true: 만료안됨)
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정이 잠겨있는지 않았는지 리턴. (true:잠기지 않음)
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    //비밀번호가 만료되지 않았는지 리턴한다. (true:만료안됨)
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정이 활성화(사용가능)인지 리턴 (true:활성화)
    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

}
