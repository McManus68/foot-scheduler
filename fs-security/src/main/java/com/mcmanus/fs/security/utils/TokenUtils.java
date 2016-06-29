package com.mcmanus.fs.security.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TokenUtils {

    public static final String MAGIC_KEY = "obfuscate";

    private static final long AUTH_DEFAULT_EXPIRY_TIME = 60 * 60 * 1000;

    private TokenUtils (){
    }

    public static String createToken(UserDetails userDetails) {
        long expires = System.currentTimeMillis() + AUTH_DEFAULT_EXPIRY_TIME;
        return joinFields(userDetails.getUsername(), expires, computeSignature(userDetails, expires));
    }

    public static boolean validateToken(String token, UserDetails userDetails) {
        String[] parts = token.split(":");
        long expires = Long.parseLong(parts[1]);
        String signature = parts[2];
        return signature.equals(TokenUtils.computeSignature(userDetails, expires));
    }

    public static String getPartFromToken(String token, int part) {
        if (null == token) {
            return null;
        }
        String[] parts = token.split(":");
        if (parts.length == 0 || part < 0 || part >= parts.length) {
            return null;
        }
        return parts[part];
    }

    /**
     * Valide un token de perte de mot de passe en :<br>
     * <li>Extrayant l'expiration de la 1ère partie (en claire)</li><br>
     * <li>Générant la signature à partir des informations du userDetails et de la date d'expiration extraite</li><br>
     * <li>Vérifiant que la signature est la même que celle envoyée dans le token</li><br>
     */
    public static boolean validateLostPasswordToken(String lostPasswordToken, String mail, String password) {
        String[] parts = lostPasswordToken.split(":");
        long expires = Long.parseLong(parts[1]);
        String signature = parts[2];
        if (expires < System.currentTimeMillis()) {
            return false;
        }
        return signature.equals(TokenUtils.computeSignature(joinFields(mail, password, expires, TokenUtils.MAGIC_KEY)));
    }

    public static String createLostPasswordToken(String mail, String password, long expires) {
        return joinFields(mail, expires,
                computeSignature(joinFields(mail, password, expires, TokenUtils.MAGIC_KEY)));
    }

    private static String computeSignature(String toSign) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!", e);
        }
        return new String(Hex.encode(digest.digest(toSign.getBytes())));
    }

    /**
     * Compute signature depending user details and expiration date.
     * @param userDetails The user details.
     * @param expires The expiration date.
     * @return The computed signature.
     */
    protected static String computeSignature(UserDetails userDetails, long expires) {
        String toSign = joinFields(userDetails.getUsername(), expires, userDetails.getPassword(), TokenUtils.MAGIC_KEY);
        return computeSignature(toSign);
    }

    private static String joinFields(Object... fields) {
        return StringUtils.join(fields, ":");
    }
}
