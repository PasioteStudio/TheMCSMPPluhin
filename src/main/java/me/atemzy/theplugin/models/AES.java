package me.atemzy.theplugin.models;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.util.Base64;

public class AES {
    private SecretKey key;
    public static int KEY_SIZE = 128;
    public AES() throws Exception {
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        keygen.init(KEY_SIZE);
        key = keygen.generateKey();
    }
    public String Encrypt(String message){
        byte[] byte_msg = message.getBytes();
        Cipher encryptionCypher = null;
        try {
            encryptionCypher = Cipher.getInstance("AES/GCM/NoPadding");
            encryptionCypher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = encryptionCypher.doFinal(byte_msg);
            String hashed = Encode(encrypted);
            return hashed;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

    }
    public static String Encode(byte[] data){
        return Base64.getEncoder().encodeToString(data);
    }
    public static byte[] decode(String str){
        return Base64.getDecoder().decode(str);
    }
}
