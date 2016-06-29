package com.mcmanus.fs.validator;

import com.mcmanus.fs.model.jpa.Player;
import com.mcmanus.fs.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
public class PlayerValidator extends EntityValidator<Player> {

    @Override
    public void validate(Object target, Errors errors) {

        Player player = (Player) target;

        /**
         * Checking username unicity
         */
        Player otherPlayer = ((UserService)srv).getByUsername(player.getUsername());

        if (otherPlayer != null && !otherPlayer.getId().equals(player.getId())) {
            errors.rejectValue("login", "error.user.duplicate.login", new Object[]{"login", player.getUsername()}, null);
            return;
        }

        /**
         * Checking mail unicity
         */
        otherPlayer = ((UserService)srv).getByMail(player.getMail());

        if (otherPlayer != null && !otherPlayer.getId().equals(player.getId())) {
            errors.rejectValue("mail", "error.user.duplicate.mail", new Object[]{"mail", player.getMail()}, null);
            return;
        }
    }
}
