package com.mcmanus.fs.model.jpa;

import com.fasterxml.jackson.annotation.JsonView;
import com.mcmanus.fs.model.views.View;

import javax.persistence.*;

@Entity
@Table(name = "player")
public class Player extends AbstractEntity {

    @Id @GeneratedValue
    @JsonView(View.Player.class)
    private Long id;

    @JsonView(View.Player.class)
    private String username;

    private String password;

    @JsonView(View.Player.class)
    private String mail;

    @JsonView(View.Player.class)
    private String role;

    @OneToOne
    @JoinColumn(name = "settings_id")
    private PlayerSettings settings;

    @Override
    public Long getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public PlayerSettings getSettings() {
        return settings;
    }

    public void setSettings(PlayerSettings settings) {
        this.settings = settings;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
