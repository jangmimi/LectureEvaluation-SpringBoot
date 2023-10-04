package com.springproject.service;

import com.springproject.model.Likey;
import com.springproject.repository.LikeyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class LikeyService {

    @Autowired
    private LikeyRepository likeyRepository;

    public Likey like(Integer evaluationID, String userId, HttpServletRequest request) {
        Likey likey = new Likey(null, userId, evaluationID, getClientIP(request));
//        likey.setUserId(userId);
//        likey.setUserIp(getClientIP(request));
//        log.info(getClientIP(request));
        return likeyRepository.save(likey);
    }

    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
