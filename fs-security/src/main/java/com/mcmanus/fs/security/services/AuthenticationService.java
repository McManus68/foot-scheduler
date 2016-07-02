package com.mcmanus.fs.security.services;

import com.mcmanus.fs.model.jpa.Player;
import com.mcmanus.fs.security.model.AuthenticatedUser;
import com.mcmanus.fs.security.model.AuthenticationToken;
import com.mcmanus.fs.security.model.Login;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AuthenticationService {

    AuthenticationToken login(Login login);

    void validateToken(String token);

    AuthenticatedUser getLoggedUser();

}
