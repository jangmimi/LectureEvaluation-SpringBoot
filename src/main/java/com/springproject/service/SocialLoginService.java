package com.springproject.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.springproject.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@Slf4j
@Service
public class SocialLoginService {
    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Autowired
    private UserService userService;

    public String getAccessTokenAndUserData(String code, RedirectAttributes redirectAttributes) {
        try {
            String responseData = getAccessTokenAndUserData(code);
            if (responseData != null) {
                access(responseData, redirectAttributes);
                return responseData;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getAccessTokenAndUserData(String code) throws IOException {
        // GitHub OAuth 인증 및 액세스 토큰 얻는 로직
        String accessToken = getAccessToken(code);

        if (accessToken != null) {
            // GitHub API를 사용하여 사용자 데이터 가져오는 로직
            String userData = getUserData(accessToken);
            return userData;
        }
        return null;
    }

    private String getAccessToken(String code) throws IOException {
        String accessToken = "";
        URL url = new URL("https://github.com/login/oauth/access_token");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36");

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()))) {
            bw.write("client_id=" + clientId + "&client_secret=" + clientSecret + "&code=" + code);
            bw.flush();
        }

        int responseCode = conn.getResponseCode();
        String responseData = getResponse(conn, responseCode);
        conn.disconnect();

        JsonParser parser = new JsonParser();
        JsonElement element =  parser.parse(responseData);

        accessToken = element.getAsJsonObject().get("access_token").getAsString();

        log.info("accessToken : " + accessToken);
        return accessToken;
    }

    private String getUserData(String accessToken) throws IOException {
        URL url = new URL("https://api.github.com/user");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36");
        conn.setRequestProperty("Authorization", "token " + accessToken);

        int responseCode = conn.getResponseCode();
        String result = getResponse(conn, responseCode);
        conn.disconnect();

        log.info("result : " + result);

        return result;
    }

    private void access(String response, RedirectAttributes redirectAttributes) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Map<String, String> map = objectMapper.readValue(response, Map.class);
            String id = map.get("id");
            String email = map.get("email");

            User user = new User();
            user.setUserId(id);
            user.setUserEmail(email);

//            userService.join(user);
        } catch (IOException e) {
            // 예외 처리
            e.printStackTrace();
        }
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
}

/*
    @GetMapping("/auth/github/callback")
    public String getCode(@RequestParam String code, RedirectAttributes redirectAttributes) throws IOException {
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

        access(responseData, redirectAttributes);
        log.info("responseData : " + responseData);

        return "redirect:/";
    }

    public void access(String response, RedirectAttributes redirectAttributes) throws IOException {

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

        String login = element.getAsJsonObject().get("id").getAsString();

        log.info("JsonObject id : " + login);
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
*/