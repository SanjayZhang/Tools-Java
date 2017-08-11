package com.sanjay.util;

import java.util.Locale;

/**
 * Created by sanjay.zsj09@gmail.com on 2017/8/8.
 */
public class PositionalNotation {

    public static String bytesToHexString(byte[] bytes) {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            int v = b & 0xFF;
            String hex = Integer.toHexString(v);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static byte[] hexStringToBytes(String hex) {
        if (hex == null || hex.equals("")) {
            return null;
        }

        String hexUpperCase = hex.toUpperCase(Locale.ENGLISH);
        int length = hexUpperCase.length() / 2;
        char[] hexChars = hexUpperCase.toCharArray();
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            bytes[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return bytes;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

}
