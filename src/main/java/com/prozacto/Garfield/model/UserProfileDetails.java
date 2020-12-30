package com.prozacto.Garfield.model;

import com.prozacto.Garfield.domain.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserProfileDetails implements UserDetails {

    private UserProfile userProfile;

    public UserProfileDetails(UserProfile user) {
        this.userProfile = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role role = Role.valueOf(userProfile.getRole());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.name()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return userProfile.getUserPassword();
    }

    @Override
    public String getUsername() {
        return userProfile.getUserName();
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
