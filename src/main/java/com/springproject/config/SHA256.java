package com.springproject.config;

import java.security.MessageDigest;

public class SHA256 {
    public static String getSHA256(String input) {
        StringBuffer result = new StringBuffer();

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            digest.update(input.getBytes("UTF-8"));

            // 바이트 배열로 해쉬를 반환
            byte[] sha256Hash = digest.digest();

            StringBuffer hexSHA256hash = new StringBuffer();

            // 256비트로 생성 => 32Byte => 1Byte(8bit) => 16진수 2자리로 변환 => 16진수 한 자리는 4bit
            for (byte b : sha256Hash) {
                String hexString = String.format("%02x", b);
                hexSHA256hash.append(hexString);
            }
            result = hexSHA256hash;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
