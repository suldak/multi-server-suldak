package com.sulsul.suldaksuldak.tool;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthTool {
    public static String encryptPassword(
            String id,
            String password
    ) throws NoSuchAlgorithmException {
        if (password == null) {
            return "";
        }

        byte[] hashValue = null; // 해쉬값

        MessageDigest md = MessageDigest.getInstance("SHA-256");

        md.reset();
        md.update(id.getBytes());

        hashValue = md.digest(password.getBytes());

        return new String(Base64.encodeBase64(hashValue));
    }
}
