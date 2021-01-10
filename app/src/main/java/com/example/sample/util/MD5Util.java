package com.example.sample.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 工具类
 *
 * @author zhangyonghong
 * @date 2019.6.13
 */
public class MD5Util {

    /**
     * 将字节数组进行 MD5 加密
     *
     * @param bytes 字节数组
     * @return 加密后字符串
     */
    public static String encrypt(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return bytes2HexString(md5.digest(bytes));
    }

    /**
     * 字节数组转十六进制字符串
     *
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    private static String bytes2HexString(byte[] bytes) {
        String base = "0123456789ABCDEF";
        char[] baseChars = new char[base.length()];
        base.getChars(0, base.length(), baseChars, 0);
        char[] chars = new char[bytes.length * 2];
        int k = 0;
        for (byte b : bytes) {
            chars[k++] = baseChars[b >>> 4 & 0xf];
            chars[k++] = baseChars[b & 0xf];
        }
        return new String(chars);
    }

}
