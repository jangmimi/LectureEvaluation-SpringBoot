package com.springproject.controller;

import com.springproject.model.User;
import com.springproject.service.LeService;
import com.springproject.util.Nmail;
import com.springproject.util.SHA256;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Properties;

@SessionAttributes("loginUser")
@Slf4j
@Controller
public class LeController {

    @Autowired
    private LeService leService;

    @RequestMapping("/")
    public String index(Model model, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser != null) {
            String userEmail = leService.getUserEmail(loginUser.getUserId());
            model.addAttribute("loginUser", session.getAttribute("loginUser"));
            return "index";
        }

        return "emailSendConfirm";
    }

    @RequestMapping("/join")
    public String join() {
        return "join";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/loginAction")
    public String loginAction(@RequestParam String userId, @RequestParam String userPw, HttpSession session) {
        User user = leService.login(userId, userPw);
        if(user != null) {
            session.setAttribute("loginUser", user);
            return "redirect:/";
        } else {
            return "login";
        }
    }

    @PostMapping("/joinAction")
    public String joinAction(@ModelAttribute User user, HttpSession session) {
        Long userNumber = leService.join(user);
        session.setAttribute("loginUser", user);

        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout(SessionStatus sessionStatus, HttpSession session) {
        sessionStatus.setComplete();;
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping("emailSendAction")
    public String emailSendAction(HttpSession session, Model model, HttpServletResponse response) {
        User loginUser = (User) session.getAttribute("loginUser");

        if(leService.getUserEmailChecked(loginUser.getUserId())) {
            model.addAttribute("msg.","이미 인증된 회원입니다.");
            return "redirect:/";
        } else {
            String host = "http://localhost:8080/LectureEvaluationSpring/";
            String from = "alwkd920101@naver.com";
            String to = leService.getUserEmail(loginUser.getUserId());
            String subject = "강의평가를 위한 이메일 인증 메일입니다.";
            String content = "다음 링크에 접속하여 이메일 인증을 진행하세요. " +
                    "<a href='" + host + "emailCheckAction.jsp?code=" + new SHA256().getSHA256(to) + "'>이메일 인증하기</a>";

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

        return "redirect:/";
    }
}
