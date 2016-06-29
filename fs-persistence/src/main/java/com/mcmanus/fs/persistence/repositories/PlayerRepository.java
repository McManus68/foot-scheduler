package com.mcmanus.fs.persistence.repositories;

import com.mcmanus.fs.model.jpa.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Player findByUsername(String username);

    Player findByMail(String mail);
}
