package com.springproject.controller;

import com.springproject.model.User;
import com.springproject.service.UserService;
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
public class UserController {

    @Autowired
    private UserService userService;

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
        User user = userService.login(userId, userPw);
        if(user != null) {
            session.setAttribute("loginUser", user);
            return "redirect:/";
        } else {
            return "login";
        }
    }

    @PostMapping("/joinAction")
    public String joinAction(@ModelAttribute User user, HttpSession session) {
        Long userNumber = userService.join(user);
        session.setAttribute("loginUser", user);

        return "emailSend";
    }

    @RequestMapping("/logout")
    public String logout(SessionStatus sessionStatus) {
        userService.logout(sessionStatus);
        return "redirect:/";
    }

    @RequestMapping("/update")
    public String update() {
        return "update";
    }

    @PostMapping("/update/{userNumber}")
    public String update(@RequestParam(required = false) Long userNumber,
                         String userId, String userPw,
                         Model model) {
        User updated = userService.update(userNumber, userId, userPw);

        model.addAttribute("loginUser", updated);
        return "redirect:/";
    }

    @RequestMapping("/emailSendAction")
    public String emailSendAction(HttpSession session, Model model, HttpServletResponse response) {
        User loginUser = (User) session.getAttribute("loginUser");

        if(userService.getUserEmailChecked(loginUser.getUserId())) {
            model.addAttribute("msg","이미 인증된 회원입니다.");
        } else {
            userService.sendEmail(loginUser.getUserNumber());
        }
        return "redirect:/";
    }

    @PostMapping("/leave/{userNumber}")
    public String leave(@RequestParam(required = false) Long userNumber, HttpSession session, SessionStatus sessionStatus) {
        userNumber = ((User) session.getAttribute("loginUser")).getUserNumber();
        log.info(userNumber.toString());
        userService.leave(userNumber);
        userService.logout(sessionStatus);

        return "redirect:/";
    }

}
