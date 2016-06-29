package com.mcmanus.fs.security.services.impl;

import com.mcmanus.fs.mail.MailFactory;
import com.mcmanus.fs.model.jpa.Player;
import com.mcmanus.fs.persistence.repositories.PlayerRepository;
import com.mcmanus.fs.security.exceptions.InvalidLostTokenException;
import com.mcmanus.fs.security.exceptions.UnknownUserMailException;
import com.mcmanus.fs.security.model.ChangePassword;
import com.mcmanus.fs.security.services.RecoveryService;
import com.mcmanus.fs.security.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class RecoveryServiceImpl implements RecoveryService {

    private static final long RECOVERY_DEFAULT_EXPIRY_TIME = 60 * 24 * 60 * 1000;

    private static final long AUTH_DEFAULT_EXPIRY_TIME = 60 * 60 * 1000;

    @Autowired
    private MailFactory mailFactory;

    @Autowired
    private PlayerRepository playerRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void sendLostPassword(String mail, String platformBaseUrl) throws UnsupportedEncodingException {
        final Player player = playerRepo.findByMail(mail);
        if (player != null) {
            final String token = TokenUtils.createLostPasswordToken(mail, player.getPassword(), System.currentTimeMillis() + RECOVERY_DEFAULT_EXPIRY_TIME);
            mailFactory.sendNewAccount(player, platformBaseUrl, token);
        } else {
            throw new UnknownUserMailException();
        }
    }

    @Override
    public void validateLostPasswordToken(String token) throws InvalidLostTokenException {
        String mail = TokenUtils.getPartFromToken(token, 0);
        final Player player = playerRepo.findByMail(mail);
        if (player != null) {
            if (!TokenUtils.validateLostPasswordToken(token, mail, player.getPassword())) {
                throw new InvalidLostTokenException();
            }
        } else {
            throw new UnknownUserMailException();
        }
    }

    @Override
    public void changeLostPassword(ChangePassword newPassword) throws InvalidLostTokenException {
        validateLostPasswordToken(newPassword.getToken());
        String mail = TokenUtils.getPartFromToken(newPassword.getToken(), 0);
        final Player player = playerRepo.findByMail(mail);
        String password = passwordEncoder.encode(newPassword.getPassword());
        player.setPassword(password);
        playerRepo.save(player);
    }
}
