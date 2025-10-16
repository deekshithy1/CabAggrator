package com.cabAggregator.Service;

import com.cabAggregator.Model.Captain;
import com.cabAggregator.Model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

public class CustomUsers implements UserDetails {

    private final String id;
    private final String email;
    private final String password;
    private final String role; // e.g., "USER" or "CAPTAIN"

    public CustomUsers(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = "USER";
    }

    public CustomUsers(Captain captain) {
        this.id = captain.getId();
        this.email = captain.getEmail();
        this.password = captain.getPassword();
        this.role = "CAPTAIN";
    }



    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // You can add role-based authorities if needed
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
