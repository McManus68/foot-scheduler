package com.mcmanus.fs.validator;

import com.mcmanus.fs.model.jpa.Player;
import com.mcmanus.fs.services.PlayerService;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
public class PlayerValidator extends EntityValidator<Player> {

    @Override
    public void validate(Object target, Errors errors) {

        Player player = (Player) target;

        Player otherPlayer = ((PlayerService)srv).getByUsername(player.getUsername());

        if (otherPlayer != null && !otherPlayer.getId().equals(player.getId())) {
            errors.reject("player.username.already.used");
            return;
        }

        otherPlayer = ((PlayerService)srv).getByMail(player.getMail());

        if (otherPlayer != null && !otherPlayer.getId().equals(player.getId())) {
            errors.reject("player.mail.already.used");
            return;
        }
    }
}
