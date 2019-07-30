package com.soaic.libcommon.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密解密工具类
 */
public class AESUtils {
    // 加密方式
    private static final String KEY_ALGORITHM = "AES";
    // 加密模式为CBC，填充模式为NoPadding
    private static final String CIPHER_ALGORITHM = "AES/CBC/NoPadding";
    // 字符集
    private static final String ENCODING = "UTF-8";
    // 偏移量
    private static final String IV_SEED = "1234567812345678";
    // 偏移量(byte[])
    private static byte[] IV_SEED_BYTES;

    static {
        try {
            IV_SEED_BYTES = IV_SEED.getBytes(ENCODING);
        } catch (UnsupportedEncodingException ignore) {
        }
    }

    /**
     * AES加密
     * @param str 密文
     * @param key 密key
     * @return String
     */
    public static String encrypt(String str, String key) {
        try {
            // 判断Key是否为16位
            if (str == null || key.length() != 16) {
                return null;
            }
            byte[] raw = key.getBytes(ENCODING);
            SecretKeySpec skySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(IV_SEED_BYTES);
            cipher.init(Cipher.ENCRYPT_MODE, skySpec, iv);
            byte[] srawt = str.getBytes(ENCODING);
            int len = srawt.length;
            /* 计算补空格后的长度 */
            while (len % 16 != 0) {
                len++;
            }
            byte[] sraw = new byte[len];
            /* 在最后空格 */
            for (int i = 0; i < len; ++i) {
                if (i < srawt.length) {
                    sraw[i] = srawt[i];
                } else {
                    sraw[i] = 32;
                }
            }
            byte[] encrypted = cipher.doFinal(sraw);
            String result = new String(Base64.encode(encrypted, Base64.DEFAULT));
            return formatString(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * AES解密
     * @param str 密文
     * @param key 密key
     * @return String
     */
    public static String decrypt(String str, String key) {
        try {
            // 判断Key是否正确 是否为16位
            if (key == null || key.length() != 16) {
                return null;
            }
            byte[] raw = key.getBytes(ENCODING);
            SecretKeySpec skySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            byte[] ivBytes = IV_SEED.getBytes(ENCODING);
            IvParameterSpec iv = new IvParameterSpec(ivBytes);
            cipher.init(Cipher.DECRYPT_MODE, skySpec, iv);
            byte[] bytes = Base64.decode(str, Base64.DEFAULT);
            bytes = cipher.doFinal(bytes);
            return new String(bytes, ENCODING);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static String formatString(String sourceStr) {
        if (sourceStr == null) {
            return null;
        }
        return sourceStr.replaceAll("\\r", "").replaceAll("\\n", "");
    }

    /**
     * byte数组转化为16进制字符串
     * @param bytes
     * @return
     */
    public static String byteToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String strHex = Integer.toHexString(aByte);
            if (strHex.length() > 3) {
                sb.append(strHex.substring(6));
            } else {
                if (strHex.length() < 2) {
                    sb.append("0").append(strHex);
                } else {
                    sb.append(strHex);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 随机生成秘钥
     */
    public static String getAesKey() {
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128);//要生成多少位，只需要修改这里即可128, 192或256
            SecretKey sk = kg.generateKey();
            byte[] b = sk.getEncoded();
            String str = byteToHexString(b);
            return str;
        } catch (NoSuchAlgorithmException e) {
            return "defaultaes";
        }
    }

}


