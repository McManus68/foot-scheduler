package com.mcmanus.fs.security.model;

import com.mcmanus.fs.model.jpa.Player;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

public class AuthenticatedUser implements UserDetails {

    private Player player;

    public AuthenticatedUser(Player player) {
        super();
        this.player = player;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(Role.valueOf(this.player.getRole()));
    }

    @Override
    public String getPassword() {
        return this.player.getPassword();
    }

    @Override
    public String getUsername() {
        return this.player.getUsername();
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

    public Player getPlayer() {
        return player;
    }
}
