package com.mcmanus.fs.security.services.impl;

import com.mcmanus.fs.model.jpa.Player;
import com.mcmanus.fs.security.AuthenticationRepositoryService;
import com.mcmanus.fs.security.model.AuthenticationToken;
import com.mcmanus.fs.security.model.Login;
import com.mcmanus.fs.security.services.AuthenticationService;
import com.mcmanus.fs.security.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final long RECOVERY_DEFAULT_EXPIRY_TIME = 60 * 24 * 60 * 1000;

    private static final long AUTH_DEFAULT_EXPIRY_TIME = 60 * 60 * 1000;

    @Autowired
    private AuthenticationRepositoryService authRepoSrv;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public AuthenticationToken login(Login login)  {

        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login.getLogin(), login.getPassword());
        final Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Player player = (Player) authentication.getPrincipal();
        return new AuthenticationToken(TokenUtils.createToken(player), player);
    }

    @Override
    public void validateToken(String token) {

        String login = TokenUtils.getPartFromToken(token, 0);
        if (login != null && !login.isEmpty()) {
            UserDetails userDetails = authRepoSrv.loadUserByUsername(login);

            if (TokenUtils.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
    }

    @Override
    public Player getLoggedUser() {
        return (Player) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
