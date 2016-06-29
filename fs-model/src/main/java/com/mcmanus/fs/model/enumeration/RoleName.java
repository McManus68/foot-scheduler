package com.mcmanus.fs.model.enumeration;

import org.springframework.security.core.GrantedAuthority;

public enum RoleName implements GrantedAuthority {

    USER,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
