package ufms.br.com.ufmsapp.utils;

import android.util.Base64;
import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class PasswordHashGenerator {

    public static String md5(String input) {

        String md5 = null;

        if (null == input) return null;

        try {

            MessageDigest digest = MessageDigest.getInstance("MD5");

            digest.update(input.getBytes(), 0, input.length());

            md5 = new BigInteger(1, digest.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }
        return md5;
    }

    public static String encryptAES(String data) {

        try {
            byte[] encodedBytes;
            Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
            byte[] encodedKey = Base64.decode(data, Base64.DEFAULT);
            SecretKey originalKey = new SecretKeySpec(encodedKey, 0,
                    encodedKey.length, "AES");
            c.init(Cipher.ENCRYPT_MODE, originalKey);
            encodedBytes = c.doFinal(data.getBytes());

            return new String(encodedBytes);
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            return null;
        }
    }

    public static String decryptAES(String data) {

        byte[] decodedBytes;
        try {
            Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
            byte[] encodedKey = Base64.decode(data, Base64.DEFAULT);
            SecretKey originalKey = new SecretKeySpec(encodedKey, 0,
                    encodedKey.length, "AES");
            c.init(Cipher.DECRYPT_MODE, originalKey);

            byte[] dataInBytes = Base64.decode(data,
                    Base64.DEFAULT);

            decodedBytes = c.doFinal(dataInBytes);
            return new String(decodedBytes);
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            e.printStackTrace();
            return null;
        }


    }
}
