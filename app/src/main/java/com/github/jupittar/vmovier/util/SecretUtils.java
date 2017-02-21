package com.github.jupittar.vmovier.util;

import android.text.TextUtils;

import com.google.android.exoplayer.util.NalUnitUtil;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class SecretUtils {
    private static final String DES = "DES";
    public static final String HMAC_MD5 = "HmacMD5";
    public static final String HMAC_SHA1 = "HmacSHA1";
    public static final String HMAC_SHA256 = "HmacSHA256";
    private static final String MODE = "DES/ECB/PKCS5Padding";

    private static String sortAndToString(ArrayList<String> encryptTextList) {
        String[] strings = new String[encryptTextList.size()];
        for (int i = 0; i < encryptTextList.size(); i++) {
            strings[i] = (String) encryptTextList.get(i);
        }
        Arrays.sort(strings);
        StringBuilder content = new StringBuilder();
        for (String string : strings) {
            content.append(string);
        }
        return content.toString();
    }

    public static String encodeDes(String key, String input) throws Exception {
        return byte2HexString(encrypt(key, input));
    }

    public static byte[] encrypt(String key, String input) throws Exception {
        return doFinal(key, 1, input.getBytes());
    }

    public static String decodeDes(String key, String input) throws Exception {
        return new String(decrypt(key, String2Byte(input)));
    }

    public static byte[] decrypt(String key, byte[] input) throws Exception {
        return doFinal(key, 2, input);
    }

    private static byte[] doFinal(String key, int opmode, byte[] input) throws Exception {
        SecureRandom sr = new SecureRandom();
        SecretKey securekey = SecretKeyFactory.getInstance(DES).generateSecret(new DESKeySpec(key.getBytes()));
        Cipher cipher = Cipher.getInstance(MODE);
        cipher.init(opmode, securekey, sr);
        return cipher.doFinal(input);
    }

    public static String byte2HexString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (byte aB : b) {
            String stmp = Integer.toHexString(aB & NalUnitUtil.EXTENDED_SAR);
            if (stmp.length() == 1) {
                sb.append("0").append(stmp);
            } else {
                sb.append(stmp);
            }
        }
        return sb.toString();
    }

    public static byte[] String2Byte(String hexString) {
        if (hexString.length() % 2 == 1) {
            return null;
        }
        byte[] ret = new byte[(hexString.length() / 2)];
        for (int i = 0; i < hexString.length(); i += 2) {
            ret[i / 2] = Integer.decode("0x" + hexString.substring(i, i + 2)).byteValue();
        }
        return ret;
    }
}
