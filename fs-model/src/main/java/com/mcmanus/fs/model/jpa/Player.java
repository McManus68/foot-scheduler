package com.mcmanus.fs.model.jpa;

import com.fasterxml.jackson.annotation.JsonView;
import com.mcmanus.fs.model.enumeration.RoleName;
import com.mcmanus.fs.model.views.View;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "player")
public class Player extends AbstractEntity implements UserDetails {

    @Id @GeneratedValue
    @JsonView(View.Player.class)
    private Long id;

    @JsonView(View.Player.class)
    private String username;

    private String password;

    @JsonView(View.Player.class)
    private String mail;

    @Enumerated(EnumType.STRING)
    @JsonView(View.Player.class)
    private RoleName role;

    @Override
    public Long getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public RoleName getRole() {
        return role;
    }

    public void setRole(RoleName role) {
        this.role = role;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}
