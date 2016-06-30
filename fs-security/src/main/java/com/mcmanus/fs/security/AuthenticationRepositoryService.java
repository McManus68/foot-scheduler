package com.mcmanus.fs.security;

import com.mcmanus.fs.persistence.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationRepositoryService implements UserDetailsService {

    @Autowired
    private PlayerRepository playerRepo;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDetails details =  playerRepo.findByUsername(username);
        if (details == null) {
            throw new UsernameNotFoundException(username);
        }
        return details;
    }
}