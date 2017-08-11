package com.sanjay.util.crypto;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 对称加密算法不要使用ECB模式
 */
public class AES {

    /**
     * 算法/工作模式/填充方式
     * JAVA6 支持PKCS5PADDING填充方式
     * Bouncy castle支持PKCS7Padding填充方式
     */
    public static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

    public static byte[] generateKey(int mode) throws NoSuchAlgorithmException {
        KeyGenerator gen = KeyGenerator.getInstance("AES");
        //128/192/256 （选用192和256的时候需要配置无政策限制权限文件--JDK6）
        gen.init(mode);
        SecretKey secretKey = gen.generateKey();
        return secretKey.getEncoded();
    }

    /**
     * @param data     the data need encrypt
     * @param password 128/192/256 bit -> 16/24/32 byte
     * @param algorithm the specific algorithm
     * @return the encrypted data
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     */
    public static byte[] encrypt(byte[] data, byte[] password, String algorithm)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            BadPaddingException, IllegalBlockSizeException,
            InvalidKeyException, InvalidAlgorithmParameterException {
        Cipher cipher = getCipher(password, algorithm, Cipher.ENCRYPT_MODE);
        return cipher.doFinal(data);
    }

    /**
     * @param data      the data need decrypt
     * @param password  128/192/256 bit -> 16/24/32 byte
     * @param algorithm algorithm
     * @return the decrypted data
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     */
    public static byte[] decrypt(byte[] data, byte[] password, String algorithm)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            BadPaddingException, IllegalBlockSizeException,
            InvalidKeyException, InvalidAlgorithmParameterException {
        Cipher cipher = getCipher(password, algorithm, Cipher.DECRYPT_MODE);
        return cipher.doFinal(data);
    }

    private static Cipher getCipher(byte[] password, String algorithm, int mode)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, InvalidAlgorithmParameterException {
        Key key = generateSecretKeySpec(password);
        Cipher cipher = Cipher.getInstance(algorithm);
        //使用CBC模式，需要一个向量iv，可增加加密算法的强度
        if (algorithm.contains("CBC")) {
            IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(password, 0, 16));
            cipher.init(mode, key, iv);
        } else {
            cipher.init(mode, key);
        }
        return cipher;
    }

    private static SecretKey generateSecretKeySpec(byte[] password) {
        return new SecretKeySpec(password, "AES");
    }

}
