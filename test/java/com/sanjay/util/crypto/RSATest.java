package com.sanjay.util.crypto;

import com.sanjay.util.PositionalNotation;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Created by sanjay.zsj09@gmail.com on 2017/8/8.
 */
public class RSATest {

    private final String encoding = "UTF-8";
    private final String originData = "RSA";

    @Test
    public void main() throws Exception {
        KeyPair kp = RSA.generateKeyPair();

        RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();
        String privateKeyM = privateKey.getModulus().toString(16);
        String privateKeyE = privateKey.getPrivateExponent().toString(16);

        System.out.println("privateKeyM:" + privateKeyM);
        System.out.println("privateKeyE:" + privateKeyE);


        RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
        String publicKeyM = publicKey.getModulus().toString(16);
        String publicKeyE = publicKey.getPublicExponent().toString(16);

        System.out.println("publicKeyM:" + publicKeyM);
        System.out.println("publicKeyE:" + publicKeyE);

        System.out.println("====encrypt=====================");
        String data = originData;
        System.out.println("data:" + data);

        BigInteger bigIntModulus = new BigInteger(publicKeyM, 16);
        BigInteger bigIntExponent = new BigInteger(publicKeyE, 16);
        PublicKey publicKeyClient = RSA.generatePublicKey(bigIntModulus, bigIntExponent);

        String algorithm = RSA.DEFAULT_CIPHER_ALGORITHM;

        byte[] bytes = RSA.encrypt(data.getBytes(encoding), publicKeyClient, algorithm);
        String hex = PositionalNotation.bytesToHexString(bytes);
        System.out.println("encrypt:" + hex);
        System.out.println("====encrypt end=================");

        System.out.println("====decrypt=====================");
        data = hex;
        System.out.println("data:" + data);

        bigIntModulus = new BigInteger(privateKeyM, 16);
        bigIntExponent = new BigInteger(privateKeyE, 16);
        PrivateKey privateKeyServer = RSA.generatePrivateKey(bigIntModulus, bigIntExponent);

        bytes = RSA.decrypt(PositionalNotation.hexStringToBytes(hex), privateKeyServer, algorithm);
        String decrypt = new String(bytes, encoding);
        System.out.println("decrypt:" + decrypt);
        System.out.println("====decrypt end=================");

        Assert.assertEquals(originData, decrypt);
    }

}