package com.sanjay.util.crypto;

import com.sanjay.util.PositionalNotation;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by sanjay.zsj09@gmail.com on 2017/8/8.
 */
public class AESTest {

    private final String password = "1234567812345678";
    private final String decryptData = "AES";
    private final String encoding = "UTF-8";
    private final String algorithm = AES.DEFAULT_CIPHER_ALGORITHM;
    private final String encryptDataHex = "2da9362edca5b9f1148c3efab393f50e";

    @Test
    public void encrypt() throws Exception {
        System.out.println("====encrypt=====================");
        String data = decryptData;
        System.out.println("data:" + data);
        System.out.println("key:" + password);

        byte[] bytes = AES.encrypt(data.getBytes(encoding), password.getBytes(encoding), algorithm);
        String hex = PositionalNotation.bytesToHexString(bytes);
        System.out.println("encrypt:" + hex);
        System.out.println("====encrypt end=================");

        Assert.assertEquals(encryptDataHex, hex);
    }

    @Test
    public void decrypt() throws Exception {
        System.out.println("====decrypt=====================");
        String data = encryptDataHex;
        System.out.println("data:" + data);
        System.out.println("key:" + password);

        byte[] bytes = AES.decrypt(PositionalNotation.hexStringToBytes(data), password.getBytes(encoding), algorithm);

        String decrypt = new String(bytes, encoding);
        System.out.println("decrypt:" + decrypt);
        System.out.println("====decrypt end=================");

        Assert.assertEquals(decryptData, decrypt);
    }

}