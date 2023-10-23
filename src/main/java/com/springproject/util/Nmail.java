package com.springproject.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class Nmail extends Authenticator {
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("이메일", "비밀번호");
    }
}
