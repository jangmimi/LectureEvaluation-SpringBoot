package com.springproject.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.springproject.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
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

    public void handleGitHubCallback(String code, RedirectAttributes redirectAttributes, HttpSession session) throws IOException {
        String responseData = requestGitHubAccessToken(code);
        processGitHubResponse(responseData, redirectAttributes, session);
        log.info("GitHub API response data: " + responseData);
    }

    private String requestGitHubAccessToken(String code) throws IOException {
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
        return getResponse(conn, responseCode);
    }

    private void processGitHubResponse(String response, RedirectAttributes redirectAttributes, HttpSession session) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(response);
        String accessToken = node.get("access_token").asText();

        URL url = new URL("https://api.github.com/user");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36");
        conn.setRequestProperty("Authorization", "token " + accessToken);

        int responseCode = conn.getResponseCode();
        String result = getResponse(conn, responseCode);

        conn.disconnect();

        redirectAttributes.addFlashAttribute("result", result);

        log.info("GitHub API response code: " + responseCode);
        log.info("GitHub API response result: " + result);

        JsonNode userNode = objectMapper.readTree(result);

        String id = userNode.get("id").asText();
        String email = userNode.get("email").asText();

        User user = new User();
        user.setUserId(id);
        user.setUserEmail(email);

        userService.join(user);

        session.setAttribute("loginUser", user);

        log.info("GitHub API user id: " + id);
        log.info("Stored user id: " + user.getUserId());
        log.info("Stored user email: " + user.getUserEmail());
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
