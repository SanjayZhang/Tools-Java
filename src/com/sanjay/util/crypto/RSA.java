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

    private static String DEFAULT_CIPHER_ALGORITHM = "RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING";

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        //PKCS#1建议的padding就占用11个字节,应此一般一次能加密的密文长度为：密钥的长度/8-11。1024/8-11=117 。
        //非对称加密一般用于加密对称加密算法的密钥，而不是直接加密内容。
        keyPairGen.initialize(2048);
        return keyPairGen.generateKeyPair();
    }

    public static RSAPublicKey generatePublicKey(BigInteger modulus, BigInteger publicExponent) throws NoSuchAlgorithmException, InvalidKeySpecException {
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, publicExponent);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    public static RSAPrivateKey generatePrivateKey(BigInteger modulus, BigInteger publicExponent) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFac = KeyFactory.getInstance("RSA");
        RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(modulus, publicExponent);
        return (RSAPrivateKey) keyFac.generatePrivate(keySpec);
    }

    public static byte[] encrypt(byte[] data, PublicKey pk) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, pk);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt(byte[] data, PrivateKey pk) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, pk);
        return cipher.doFinal(data);
    }

    public static void main(String[] args) throws Exception {
        KeyPair kp = generateKeyPair();

        RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
        String publicKeyM = publicKey.getModulus().toString(16);
        String publicKeyE = publicKey.getPublicExponent().toString(16);

        System.out.println("publicKeyM:" + publicKeyM);
        System.out.println("publicKeyE:" + publicKeyE);

        RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();
        String privateKeyM = privateKey.getModulus().toString(16);
        String privateKeyE = privateKey.getPrivateExponent().toString(16);

        System.out.println("privateKeyM:" + privateKeyM);
        System.out.println("privateKeyE:" + privateKeyE);

        String data = "RSA";
        System.out.println("data:" + data);

        System.out.println("====encrypt=====================");

        BigInteger bigIntModulus = new BigInteger(publicKeyM, 16);
        BigInteger bigIntExponent = new BigInteger(publicKeyE, 16);
        PublicKey publicKeyClient = RSA.generatePublicKey(bigIntModulus, bigIntExponent);

        byte[] bytes = RSA.encrypt(data.getBytes(), publicKeyClient);
        System.out.println("encrypt:" + new String(bytes));

        System.out.println("====decrypt=====================");

        bigIntModulus = new BigInteger(privateKeyM, 16);
        bigIntExponent = new BigInteger(privateKeyE, 16);
        PrivateKey privateKeyServer = RSA.generatePrivateKey(bigIntModulus, bigIntExponent);

        bytes = RSA.decrypt(bytes, privateKeyServer);
        System.out.println("decrypt:" + new String(bytes));
    }
}
