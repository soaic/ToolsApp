package com.soaic.libcommon.utils;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * Base64 编码原理：
 * 1. 把原始字符串中转二进制，8位一个字符，不足8位在前补0，3个字符为一组，每一组24位，最后一组不足24位用0补足 （Tip:中文UTF-8编码表示三个字符）
 * 2. 最后得到的最少24位的二进制中，每6位一组转成十进制作为索引到指定ascii字符串中取值，如果最后6位中全部为000000则用‘=’好代替
 * 3. 最后把得到的ascii按顺序拼接成字符串
 * Base64 解码原理：
 * 1. 把加密的字符串分别通过指定的ascii码获取索引
 * 2. 再分别把索引转成6位的二进制,不足6位在前面补0，按顺序拼接起来
 * 3. 最后以8位为一个字符，先转成int，再转byte, 存储在byte数组中，转成String
 */
public class Base64Util {

    private static String tables = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    public static String encode(String str){
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        StringBuilder codeStr = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String rs = Long.toString(bytes[i] & 0xff, 2);
            int a = rs.length() / 8;
            int b = rs.length() % 8;
            int c = b == 0 ? a * 8 : ( a + 1 ) * 8;
            String s = String.format(Locale.getDefault(),"%0" + c + "d", Long.parseLong(rs));
            codeStr.append(s);
        }
        int codeLength = codeStr.toString().length();
        for (int i = 0; codeLength % 24 != 0 && i < 24 - codeLength % 24; i++){
            codeStr.append("0");
        }
        codeLength = codeStr.toString().length();
        StringBuilder codeStrResult = new StringBuilder();
        for (int i = 0; i < codeLength / 6; i++){
            String code = codeStr.substring(i * 6, i * 6 + 6);
            if (i * 6 < codeLength - 24 + codeLength % 24){
                codeStrResult.append(tables.charAt(Integer.valueOf(code,2)));
            } else {
                if (!code.equals("000000")) {
                    codeStrResult.append(tables.charAt(Integer.valueOf(code, 2)));
                } else {
                    codeStrResult.append("=");
                }
            }
        }
        return codeStrResult.toString();
    }

    public static String decode(String code) {
        code = code.replace("=","");
        char[] charArray = code.toCharArray();
        StringBuilder resultCode = new StringBuilder();
        for (char ca : charArray) {
            String binary = Long.toBinaryString(tables.indexOf(ca));
            String s = String.format(Locale.getDefault(),"%06d", Long.parseLong(binary));
            resultCode.append(s);
        }
        byte[] result = new byte[resultCode.length()];
        for (int i = 0; i < resultCode.toString().length() / 8; i++) {
            String code8 = resultCode.substring(i * 8, (i + 1) * 8);
            result[i] = (byte)Integer.parseInt(code8, 2);
        }
        return new String(result);
    }

    public static String encode2(String str){
        StringBuffer result = new StringBuffer();
        byte[] data = str.getBytes(StandardCharsets.UTF_8);
        int leftChar = 0;
        int bits = 0;
        for (int i = 0; i < data.length; i++) {
            byte c = data[i]  ;
            leftChar = (leftChar << 8) | (c & 0xff);
            bits += 8;

            while ( bits >= 6) {
                int index = (leftChar >> (bits - 6)) & 0x3f;
                bits -= 6;
                result.append(tables.charAt(index));
            }
        }
        return  result.toString();
    }
}
