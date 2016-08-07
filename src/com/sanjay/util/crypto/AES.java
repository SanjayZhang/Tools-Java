package com.sanjay.util.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
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
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

    private static byte[] generateKey() throws NoSuchAlgorithmException {
        KeyGenerator gen = KeyGenerator.getInstance("AES");
        //128/192/256 （选用192和256的时候需要配置无政策限制权限文件--JDK6）
        gen.init(128);
        SecretKey secretKey = gen.generateKey();
        return secretKey.getEncoded();
    }

    /**
     * @param data the data need encrypt
     * @param password 128/192/256 bit -> 16/24/32 byte
     * @return the encrypted data
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     */
    public static byte[] encrypt(byte[] data, byte[] password)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            BadPaddingException, IllegalBlockSizeException,
            InvalidKeyException, InvalidAlgorithmParameterException {
        Key key = generateSecretKeySpec(password);
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        //使用CBC模式，需要一个向量iv，可增加加密算法的强度
        if (DEFAULT_CIPHER_ALGORITHM.contains("CBC")) {
            IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(password, 0, 16));
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, key);
        }
        return cipher.doFinal(data);
    }

    /**
     * @param data     the data need decrypt
     * @param password 128/192/256 bit -> 16/24/32 byte
     * @return the decrypted data
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     */
    public static byte[] decrypt(byte[] data, byte[] password)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            BadPaddingException, IllegalBlockSizeException,
            InvalidKeyException, InvalidAlgorithmParameterException {
        Key key = generateSecretKeySpec(password);
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        //使用CBC模式，需要一个向量iv，可增加加密算法的强度
        if (DEFAULT_CIPHER_ALGORITHM.contains("CBC")) {
            IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(password, 0, 16));
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, key);
        }
        return cipher.doFinal(data);
    }

    private static SecretKey generateSecretKeySpec(byte[] password) {
        return new SecretKeySpec(password, "AES");
    }

    public static void main(String[] args) throws Exception {

        final String encoding = "UTF-8";

        String data = "AES";
        System.out.println("data:" + data);

        byte[] keyBytes = generateKey();
        System.out.println("key:" + new String(keyBytes, encoding));

        System.out.println("====encrypt=====================");
        byte[] bytes = encrypt(data.getBytes(encoding), keyBytes);
        System.out.println("encrypt:" + new String(bytes, encoding));

        System.out.println("====decrypt=====================");
        bytes = decrypt(bytes, keyBytes);
        System.out.println("decrypt:" + new String(bytes, encoding));
    }
}
