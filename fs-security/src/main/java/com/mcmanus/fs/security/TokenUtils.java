package com.mcmanus.fs.security;

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

    /**
     * Create authentication token from user details.
     * @param userDetails The user details.
     * @return A token.
     */
    public static String createAuthToken(UserDetails userDetails) {
        long expires = System.currentTimeMillis() + AUTH_DEFAULT_EXPIRY_TIME;
        return createAuthToken(userDetails, expires);
    }

    /**
     * Create authentication token from user details.
     * @param userDetails The user details.
     * @return A token.
     */
    public static String createAuthToken(UserDetails userDetails, long expires) {
        return joinFields(userDetails.getUsername(), expires, computeAuthSignature(userDetails, expires));
    }

    /**
     * Validate a token.
     * @param authToken The token.
     * @param userDetails The user details.
     * @return True if the token is valid, otherwise false.
     */
    public static boolean validateAuthToken(String authToken, UserDetails userDetails) {
        String[] parts = authToken.split(":");
        long expires = Long.parseLong(parts[1]);
        String signature = parts[2];
        return signature.equals(TokenUtils.computeAuthSignature(userDetails, expires));
    }

    /**
     * Extract information from token.
     * @param token The token.
     * @param part The index of the required information.
     * @return The extracted information.
     */
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
     * Compute signature depending user details and expiration date.
     * @param userDetails The user details.
     * @param expires The expiration date.
     * @return The computed signature.
     */
    protected static String computeAuthSignature(UserDetails userDetails, long expires) {
        String toSign = joinFields(userDetails.getUsername(), expires, userDetails.getPassword(), TokenUtils.MAGIC_KEY);
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!", e);
        }
        return new String(Hex.encode(digest.digest(toSign.getBytes())));
    }

    /**
     * Join fields.
     * @param fields The fields.
     * @return The joined fields.
     */
    private static String joinFields(Object... fields) {
        return StringUtils.join(fields, ":");
    }
}
