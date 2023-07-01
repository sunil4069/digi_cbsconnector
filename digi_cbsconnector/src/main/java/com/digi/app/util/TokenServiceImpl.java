package com.digi.app.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

@Slf4j
@Service
public class TokenServiceImpl implements TokenService {
    public static final String SECRET ="IAMDIGINEPALSECRET";
    private static SecretKeySpec secretKey;
    private static byte[] key;

    @Override
    public void setKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            log.error("set key encryption exception\n" + e);
        } catch (UnsupportedEncodingException e) {
            log.error("set key encryption exception\n" + e);
        }
    }

    @Override
    public String encrypt(String strToEncrypt) {
        try {
            setKey(SECRET);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            log.error("error while encryption\n" + e);
        }
        return null;
    }

    @Override
    public String decrypt(String strToDecrypt) {
        try {
            setKey(SECRET);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            log.error("error while decryption\n" + e);
        }
        return null;
    }
}
