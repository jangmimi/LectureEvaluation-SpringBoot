package com.springproject.controller;

import com.springproject.model.User;
import com.springproject.service.LeService;
import com.springproject.util.SHA256;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

        return "emailSend";
    }

    @RequestMapping("/logout")
    public String logout(SessionStatus sessionStatus) {
        sessionStatus.setComplete();;
        return "redirect:/";
    }

    @RequestMapping("emailSendAction")
    public String emailSendAction(HttpSession session, Model model, HttpServletResponse response) {
        User loginUser = (User) session.getAttribute("loginUser");

        if(leService.getUserEmailChecked(loginUser.getUserId())) {
            model.addAttribute("msg","이미 인증된 회원입니다.");
        } else {
            leService.sendEmail(loginUser.getUserId());
        }
        return "redirect:/";
    }

    @RequestMapping("emailCheckAction")
    public String emailCheckAction(HttpSession session, Model model, @RequestParam(required = false) String code) {
        User loginUser = (User) session.getAttribute("loginUser");
        boolean isRight = SHA256.getSHA256(loginUser.getUserEmail()).equals(code);
        if(isRight) {
            leService.setUserEmailChecked(loginUser.getUserId());
        }

        return "redirect:/";
    }
}
