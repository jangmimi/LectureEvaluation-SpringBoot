package com.springproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Slf4j
@Service
public class EmailService {

    @Value("${admin.email}")
    private String adminEmail;  // 관리자 이메일 주소, application.yml에서 설정됨

    @Value("${spring.mail.from}")
    private String from;        // 발신자 이메일 주소, application.yml에서 설정됨

    @Autowired
    private JavaMailSender mailSender;

    /**
     * 인증 이메일 발송 (관리자 -> 사용자)
     *
     * @param to      수신자 이메일 주소
     * @param subject 이메일 제목
     * @param content 이메일 내용
     */
    public void sendEmail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
        } catch (Exception e) {
            log.error("인증 이메일 발송 중 오류 발생 : " + e.getMessage(), e);
        }
    }

    /**
     * 강의 평가 신고 이메일 발송 (사용자 -> 관리자)
     *
     * @param to      수신자 이메일 주소
     * @param subject 이메일 제목
     * @param content 이메일 내용
     */
    public void reportEmail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
        } catch (Exception e) {
            log.error("강의 평가 신고 이메일 발송 중 오류 발생 : " + e.getMessage(), e);
        }
    }
}
