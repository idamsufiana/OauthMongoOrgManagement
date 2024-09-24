package com.ida.management.entities;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Accessors(chain = true)
public class UserPrincipal implements UserDetails {

    private final Account user;

    public UserPrincipal(Account user) {
        this.user = user;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Extract list of roles
        this.user.getRole().forEach(r -> {
            GrantedAuthority authority = new SimpleGrantedAuthority(r);
            authorities.add(authority);
        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

}
