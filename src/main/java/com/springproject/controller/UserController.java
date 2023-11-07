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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@SessionAttributes("loginUser")
@Slf4j
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SocialLoginService socialLoginService;

    @RequestMapping("/join")
    public String join() {
        return "user/join";
    }

    @RequestMapping("/login")
    public String login() {
        return "user/login";
    }

    @PostMapping("/loginAction")
    @ResponseBody
    public int loginAction(@RequestParam String userId, @RequestParam String userPw, HttpSession session) {
        User user = userService.login(userId, userPw);

        if (user != null) {
            session.setAttribute("loginUser", user);
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

//    @GetMapping("/auth/github/callback")
//    public String getCode2(@RequestParam String code, RedirectAttributes redirectAttributes) {
//        String responseData = socialLoginService.getAccessTokenAndUserData(code, redirectAttributes);
//        log.info("responseData : " + responseData);
//        return "redirect:/";
//    }

    @GetMapping("/auth/github/callback")
    public String getCode(@RequestParam String code, RedirectAttributes redirectAttributes, HttpSession session) throws IOException {
        URL url = new URL("https://github.com/login/oauth/access_token");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36");

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()))) {
            bw.write("client_id=Iv1.3a84c6d29e6bb326&client_secret=637f265d53cbad00624cef25569ef0516757495e&code=" + code);
            bw.flush();
        }
        int responseCode = conn.getResponseCode();
        String responseData = getResponse(conn, responseCode);

        conn.disconnect();

        access(responseData, redirectAttributes, session);
        log.info("responseData : " + responseData);

        return "redirect:/";
    }

    public void access(String response, RedirectAttributes redirectAttributes, HttpSession session) throws IOException {

        // JSON 데이터 처리를 위해 Spring Boot Jackson 라이브러리 사용
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> map = objectMapper.readValue(response, Map.class);
        String access_token = map.get("access_token");

        URL url = new URL("https://api.github.com/user");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36");
        conn.setRequestProperty("Authorization", "token " + access_token);

        int responseCode = conn.getResponseCode();

        String result = getResponse(conn, responseCode);

        conn.disconnect();

        redirectAttributes.addFlashAttribute("result", result);

        log.info("responseCode : " + responseCode);
        log.info("result : " + result);

        JsonParser parser = new JsonParser();
        JsonElement element =  parser.parse(result);

        String id = element.getAsJsonObject().get("id").getAsString();
        String email = element.getAsJsonObject().get("email").getAsString();

        User user = new User();
        user.setUserId(id);
        user.setUserEmail(email);

        userService.join(user);

        session.setAttribute("loginUser", user);

        log.info("JsonObject id : " + id);
        log.info("user id : " + user.getUserId());
        log.info("user email : " + user.getUserEmail());

    }

    private String getResponse(HttpURLConnection conn, int responseCode) throws IOException {
        StringBuilder sb = new StringBuilder();
        if (responseCode == 200) {
            try (InputStream is = conn.getInputStream();
                 BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                for (String line = br.readLine(); line != null; line = br.readLine()) {
                    sb.append(line);
                }
            }
        }
        return sb.toString();
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
            log.info("updated : " + updated);
            log.info("updated id : " + updated.getUserId());
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
