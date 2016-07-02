package com.mcmanus.fs.security.services.impl;

import com.mcmanus.fs.model.jpa.Player;
import com.mcmanus.fs.persistence.repositories.PlayerRepository;
import com.mcmanus.fs.security.model.AuthenticatedUser;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService, UserDetailsService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PlayerRepository playerRepo;

    @Override
    public AuthenticationToken login(Login login) {
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        final Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();
        return new AuthenticationToken(TokenUtils.createToken(user), user);
    }

    @Override
    public void validateToken(String token) {

        String login = TokenUtils.getPartFromToken(token, 0);
        if (login != null && !login.isEmpty()) {
            UserDetails userDetails = loadUserByUsername(login);

            if (TokenUtils.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
    }

    @Override
    public AuthenticatedUser getLoggedUser() {
        return (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Player player = playerRepo.findByUsername(username);
        if (player == null) {
            throw new UsernameNotFoundException(username);
        }
        return new AuthenticatedUser(player);
    }
}
