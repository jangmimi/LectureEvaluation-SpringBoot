package com.springproject.controller;

import com.springproject.model.User;
import com.springproject.service.LeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;

@SessionAttributes("loginUser")
@Slf4j
@Controller
public class LeController {

    @Autowired
    private LeService leService;

    @RequestMapping("/")
    public String index(Model model, HttpSession session) {
        model.addAttribute("loginUser", session.getAttribute("loginUser"));
        return "index";
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
}
