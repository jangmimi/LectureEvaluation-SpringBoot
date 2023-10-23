package com.springproject.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JasyptConfigTest {

    @Test
    void encryptTest() {
        String url = "enc_test_url";
        String id = "enc_test_id";
        String password = "enc_test_pw";

        System.out.println("db_url : " + jasyptEncoding(url));
        System.out.println("db_id : " + jasyptEncoding(id));
        System.out.println("db_password : " + jasyptEncoding(password));
    }

    public String jasyptEncoding(String value) {
        String key = "비밀번호";
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword(key);
        return pbeEnc.encrypt(value);
    }
}