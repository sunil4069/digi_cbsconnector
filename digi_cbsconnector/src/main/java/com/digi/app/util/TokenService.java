package com.digi.app.util;

public interface TokenService {
    void setKey(String key);

    String encrypt(String strToEncrypt);

    String decrypt(String strToDecrypt);
}
