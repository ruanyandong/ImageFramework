package com.miracle.library.utils;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author miracle
 * @date 2019-09-25
 * @email ruanyandongai@gmail.com
 * @blog https://ruanyandong.github.io
 */
public class Md5FileNameGenerator{

    private static final String HASH_ALGORITHM = "MD5";
    /**
     * 10位数字+26个字母
     */
    private static final int RADIX = 10+26;

    public static String generate(String imageUri){
        byte[] md5 = getMD5(imageUri.getBytes());
        BigInteger bi = new BigInteger(md5).abs();
        return bi.toString(RADIX);
    }

    private static byte[] getMD5(byte[] data){
        byte[] hash = null;
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            digest.update(data);
            hash = digest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }
}
