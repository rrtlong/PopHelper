package com.moli.module.net.encryp;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 接口加密算法工具类
 *
 * @author zixiaojun
 * @date 2018/4/21
 * @copyright Moli Teams
 */
public class DigestKit {

    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private static final char[] DIGITS_LOWER;
    private static final char[] DIGITS_UPPER;

    static {
        DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        DIGITS_UPPER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    }

    private DigestKit() {
    }

    public static String md5Hex(String data) {
        return encodeHexString(md5(data));
    }

    public static String md5Hex(byte[] data) {
        return encodeHexString(md5(data));
    }

    private static byte[] md5(byte[] data) {
        return getMd5Digest().digest(data);
    }

    private static byte[] md5(String data) {
        return md5(getBytesUtf8(data));
    }

    private static MessageDigest getMd5Digest() {
        return getDigest("MD5");
    }

    private static MessageDigest getDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException var2) {
            throw new IllegalArgumentException(var2);
        }
    }

    private static String encodeHexString(byte[] data) {
        return new String(encodeHex(data));
    }

    private static char[] encodeHex(byte[] data) {
        return encodeHex(data, true);
    }

    private static char[] encodeHex(byte[] data, boolean toLowerCase) {
        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    private static char[] encodeHex(byte[] data, char[] toDigits) {
        int l = data.length;
        char[] out = new char[l << 1];
        int i = 0;

        for (int var5 = 0; i < l; ++i) {
            out[var5++] = toDigits[(240 & data[i]) >>> 4];
            out[var5++] = toDigits[15 & data[i]];
        }

        return out;
    }

    private static byte[] getBytesUtf8(String string) {
        return getBytes(string, UTF_8);
    }

    private static byte[] getBytes(String string, Charset charset) {
        return string == null ? null : string.getBytes(charset);
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }
}
