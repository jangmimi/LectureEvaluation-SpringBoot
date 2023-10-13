package com.springproject.service;

import com.springproject.model.User;
import com.springproject.repository.UserRepository;
import com.springproject.util.Nmail;
import com.springproject.util.SHA256;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.support.SessionStatus;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Properties;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 회원가입
     */
    @Transactional
    public Long join(User user) {
        user.setUserEmailHash(SHA256.getSHA256(user.getUserEmail()));

        // 중복 회원 검증
        validateDuplicateUser(user);
        userRepository.save(user);
        sendEmail(user.getUserId());

        return user.getUserNumber();
    }

    /**
     * 중복회원검증
     */
    public void validateDuplicateUser(User user) {
//        userRepository.findByUserId(user.getUserId())
//                .ifPresent(m -> {
//                    throw new IllegalStateException("이미 존재하는 회원입니다.");
//                });
        if (userRepository.findByUserId(user.getUserId()).isPresent()) {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 로그인
     */
    public User login(String uesrId, String userPw) {
        Optional<User> findUser = userRepository.findByUserId(uesrId);
        if(findUser.isPresent()) {
            if(userPw.equals(findUser.get().getUserPw())) {
                return findUser.orElse(null);
            }
        }
        return null;
    }

    public void logout(SessionStatus sessionStatus) {
        sessionStatus.setComplete();;
    }

    public User findUser(Long userNumber) {
        Optional<User> find = userRepository.findByUserNumber(userNumber);
        return find.orElse(null);
    }

    public String getUserEmail(String userId) {
        Optional<User> findUser = userRepository.findByUserId(userId);
        return findUser.map(User::getUserEmail).orElse(null);
    }

    public boolean getUserEmailChecked(String userId) {
        Optional<User> findUser = userRepository.findByUserId(userId);
        return findUser.filter(user -> user.getUserEmailChecked() == 1).isPresent();
    }

    @Transactional
    public boolean setUserEmailChecked(String userId) {
        Optional<User> findUser = userRepository.findByUserId(userId);
        if(findUser.isPresent()) {
            findUser.get().setUserEmailChecked(1);
            log.info(String.valueOf(findUser.get().getUserEmailChecked()));
            return true;
        }
        return false;
    }

    public void sendEmail(String userId) {
        String host = "http://localhost:8080/";
        String from = "alwkd920101@naver.com";
        String to = getUserEmail(userId);
        String subject = "강의평가를 위한 이메일 인증 메일입니다.";
        String content = "다음 링크에 접속하여 이메일 인증을 진행하세요. " +
                "<a href='" + host + "emailCheckAction?code=" + SHA256.getSHA256(to) + "'>이메일 인증하기</a>";

        Properties p = new Properties();
        p.put("mail.smtp.starttls.enable", "true");     // gmail은 true 고정
        p.put("mail.smtp.host", "smtp.naver.com");      // smtp 서버 주소
        p.put("mail.smtp.auth","true");                 // gmail은 true 고정
        p.put("mail.smtp.port", "587");                 // 네이버 포트

        try {
            Authenticator auth = new Nmail();
            Session ses = Session.getInstance(p, auth);
            ses.setDebug(true);
            MimeMessage msg = new MimeMessage(ses);
            msg.setSubject(subject);
            Address fromAddr = new InternetAddress(from);
            msg.setFrom(fromAddr);
            Address toAddr = new InternetAddress(to);
            msg.addRecipient(Message.RecipientType.TO, toAddr);
            msg.setContent(content,"text/html;charset=UTF-8");
            Transport.send(msg);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void reportAction(String reportTitle, String reportContent, String userId) {
        String host = "http://localhost:8080/";
        String from = "alwkd920101@naver.com";
        String to = "alwkd920101@naver.com";
        String subject = "강의평가 사이트에서 접수된 신고 메일입니다.";
        String content = "신고자 : " + userId +
                "<br>제목: " + reportTitle +
                "<br>내용: " + reportContent;

        Properties p = new Properties();
        p.put("mail.smtp.starttls.enable", "true");     // gmail은 true 고정
        p.put("mail.smtp.host", "smtp.naver.com");      // smtp 서버 주소
        p.put("mail.smtp.auth","true");                 // gmail은 true 고정
        p.put("mail.smtp.port", "587");                 // 네이버 포트

        try {
            Authenticator auth = new Nmail();
            Session ses = Session.getInstance(p, auth);
            ses.setDebug(true);
            MimeMessage msg = new MimeMessage(ses);
            msg.setSubject(subject);
            Address fromAddr = new InternetAddress(from);
            msg.setFrom(fromAddr);
            Address toAddr = new InternetAddress(to);
            msg.addRecipient(Message.RecipientType.TO, toAddr);
            msg.setContent(content,"text/html;charset=UTF-8");
            Transport.send(msg);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 회원탈퇴
     * */
    @Transactional
    public void leave(Long userNumber) {
        userRepository.deleteByUserNumber(userNumber);
    }

    /**
     * 회원정보수정
     * */
    @Transactional
    public void update(Long userNumber, String userId, String userPw) {
        Optional<User> find = userRepository.findByUserNumber(userNumber);
        if (find.isPresent()) {
            User updated = find.get();
            find.get().setUserId(userId);
            find.get().setUserPw(userPw);
            userRepository.save(updated);
            log.info("updated : " + updated);
        }
    }

}
