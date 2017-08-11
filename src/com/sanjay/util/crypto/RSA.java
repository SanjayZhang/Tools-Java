package com.sanjay.util.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

/**
 * Created by sanjay.zsj09@gmail.com on 2016/7/27.
 */
public class RSA {

    public static final String DEFAULT_CIPHER_ALGORITHM = "RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING";

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        //PKCS#1建议的padding就占用11个字节,应此一般一次能加密的密文长度为：密钥的长度/8-11。1024/8-11=117 。
        //非对称加密一般用于加密对称加密算法的密钥，而不是直接加密内容。
        keyPairGen.initialize(2048);
        return keyPairGen.generateKeyPair();
    }

    public static RSAPublicKey generatePublicKey(BigInteger modulus, BigInteger publicExponent)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, publicExponent);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    public static RSAPrivateKey generatePrivateKey(BigInteger modulus, BigInteger publicExponent)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(modulus, publicExponent);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    public static byte[] encrypt(byte[] data, PublicKey pk, String algorithm)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, pk);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt(byte[] data, PrivateKey pk, String algorithm)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, pk);
        return cipher.doFinal(data);
    }

}
