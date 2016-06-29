package com.mcmanus.fs.security.services;

import com.mcmanus.fs.security.exceptions.InvalidLostTokenException;
import com.mcmanus.fs.security.model.ChangePassword;

import java.io.UnsupportedEncodingException;

public interface RecoveryService {

    void sendLostPassword(String mail, String platformBaseUrl) throws UnsupportedEncodingException;

    void validateLostPasswordToken(String token) throws InvalidLostTokenException;

    void changeLostPassword(ChangePassword newPassword) throws InvalidLostTokenException;

}
