package com.mcmanus.fs.services.impl;

import com.mcmanus.fs.model.enumeration.RoleName;
import com.mcmanus.fs.model.jpa.Player;
import com.mcmanus.fs.persistence.repositories.PlayerRepository;
import com.mcmanus.fs.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl extends EntityServiceImpl<Player> implements PlayerService {

    @Autowired(required = false)
    PasswordEncoder pwdEncoder;

    @Override
    public Long save(Player player) {
        player.setRole(RoleName.USER);
        return super.save(player);
    }

    @Override
    public Long update(Player player) {
        Player existingPlayer =  repo.findOne(player.getId());
        player.setPassword(existingPlayer.getPassword());
        return super.update(player);
    }

    @Override
    public Player getByUsername(String username) {
        return ((PlayerRepository) repo).findByUsername(username);
    }

    @Override
    public Player getByMail(String mail) {
        return ((PlayerRepository) repo).findByMail(mail);
    }
}
