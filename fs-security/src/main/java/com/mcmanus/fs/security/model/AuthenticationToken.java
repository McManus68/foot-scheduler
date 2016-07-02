package com.mcmanus.fs.security.model;


import com.mcmanus.fs.model.jpa.Player;

public class AuthenticationToken {

    private String token;
    private Long uuid;
    private String username;

    public AuthenticationToken(String token, AuthenticatedUser user) {
        super();
        this.token = token;
        this.uuid = user.getPlayer().getId();
        this.username = user.getUsername();
    }

    public AuthenticationToken() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
