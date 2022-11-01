package dev.dracarys.com.hospitalquerysystem.configs;

import dev.dracarys.com.hospitalquerysystem.dominio.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.Optional;

public class DetailsUserData implements UserDetails{
    private final transient Optional<UserModel> user;

    public DetailsUserData(Optional<UserModel> user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return user.get().getRoles();
    }

    @Override
    public String getPassword() {
        if(this.user == null) {
            return "";
        }
        return user.orElse(new UserModel()).getPassword();
    }

    @Override
    public String getUsername() {
        if(this.user == null) {
            return "";
        }
        return user.orElse(new UserModel()).getUsername();
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
