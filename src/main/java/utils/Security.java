package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Security {
    public static final MessageDigest sha256;

    static {
        try {
            sha256 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public String EncryptSHA256(String input){
        byte[] encrypted_b = sha256.digest(input.getBytes());
        String encrypted = Base64.getEncoder().encodeToString(encrypted_b);
        return encrypted;
    }
}
