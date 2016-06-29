package com.mcmanus.fs.services;


import com.mcmanus.fs.model.jpa.Player;

public interface UserService extends EntityService<Player> {

    Player getByUsername(String username);

    Player getByMail(String mail);

}
