package com.springproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.springproject.config.SHA256;
import com.springproject.model.User;
import com.springproject.service.SocialLoginService;
import com.springproject.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@SessionAttributes("loginUser")
@Slf4j
@Controller
public class UserController {
    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Autowired
    private UserService userService;

    @Autowired
    private SocialLoginService socialLoginService;

    @RequestMapping("/join")
    public String join() {
        return "user/join";
    }

    @RequestMapping("/login")
    public String login(@CookieValue(value = "rememberedId", required = false) String rememberedId, Model model) {
        model.addAttribute("rememberedId", rememberedId); // 쿠키가 있는 경우, 저장 이메일 표시

        return "user/login";
    }

    @PostMapping("/loginAction")
    @ResponseBody
    public int loginAction(@Valid User imuser, Errors errors,
                           @RequestParam String userId, @RequestParam String userPw,
                           @RequestParam(required = false) boolean saveId, HttpServletResponse response, HttpServletRequest request,
                           HttpSession session, Model model) {
//    public int loginAction(@RequestParam String userId, @RequestParam String userPw, HttpSession session) {
        if (errors.hasErrors()) {
            model.addAttribute("user", imuser);

            Map<String, String> validatorResult = userService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
        }
        User user = userService.login(userId, userPw);

        if (user != null) {
            session.setAttribute("loginUser", user);

            // 쿠키 작업
            if (saveId) {
                // 아이디를 저장할 쿠키 생성
                Cookie cookie = new Cookie("rememberedId", userId);
                cookie.setMaxAge(30 * 24 * 60 * 60); // 30일 동안 유지
                cookie.setPath("/"); // 모든 경로에 쿠키 설정
                response.addCookie(cookie);
            } else {
                // 아이디 저장을 원치 않는 경우 쿠키 제거
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if ("rememberedId".equals(cookie.getName())) {
                            cookie.setValue(null);
                            cookie.setMaxAge(0);
                            cookie.setPath("/");
                            response.addCookie(cookie);
                            break;
                        }
                    }
                }
            }
            return 1;

        } else {
            return 0;
        }
    }

    @PostMapping("/joinAction")
    @ResponseBody
    public int joinAction(@ModelAttribute User user, HttpSession session) {
        Long userNumber = userService.join(user);

        if (userNumber != null) {
            session.setAttribute("loginUser", user);
            return 1;
        } else {
            return 0;
        }
    }

    @GetMapping("/auth/github/callback")
    public String getCode(@RequestParam String code, RedirectAttributes redirectAttributes, HttpSession session) throws IOException {
        socialLoginService.handleGitHubCallback(code, redirectAttributes, session);
        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout(SessionStatus sessionStatus) {
        userService.logout(sessionStatus);
        return "redirect:/";
    }

    @RequestMapping("/update")
    public String update() {
        return "user/update";
    }

    @PostMapping("/update/{userNumber}")
    @ResponseBody
    public boolean update(@RequestParam(required = false) Long userNumber,
                         String userId, String userPw, Model model) {
        log.info("UserController userNumber : " + userNumber);
        User updated = userService.update(userNumber, userId, userPw);

        if (updated != null) {
            model.addAttribute("loginUser", updated);
            return true;
        } else {
            return false;
        }
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

    @RequestMapping("/emailCheckAction")
    public String emailCheckAction(@RequestParam(required = false) String code,
                                   HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");
        boolean isRight = SHA256.getSHA256(loginUser.getUserEmail()).equals(code);

        if(isRight) {
            userService.setUserEmailChecked(loginUser.getUserId());
        }

        return "redirect:/";
    }

    @PostMapping("/leave/{userNumber}")
    public String leave(@RequestParam(required = false) Long userNumber,
                        HttpSession session, SessionStatus sessionStatus) {
        userService.leave(userNumber, sessionStatus);

        return "redirect:/";
    }

    // ajax로 아이디 중복 체크
    @PostMapping("/checkId")
    @ResponseBody
    public int validateDuplicateUser(@RequestParam String userId) {
        boolean checkId = userService.validateDuplicateId(userId);

        return checkId ? 0 : 1; // 0일 경우 중복 아이디
    }
}
