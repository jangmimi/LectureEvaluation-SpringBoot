package com.springproject.service;

import com.springproject.model.Likey;
import com.springproject.repository.LikeyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.Like;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class LikeyService {

    @Autowired
    private LikeyRepository likeyRepository;

    public Likey like(Long evaluationID, Long userNumber, HttpServletRequest request) {
        Likey likey = new Likey();
        likey.setUserNumber(userNumber);
        likey.setEvaluationId(evaluationID);
        likey.setUserIp(getClientIP(request));
        log.info(likey.toString());
        return likeyRepository.save(likey);
    }

    public static String getClientIP(HttpServletRequest request) {
        String[] headerNames = {"X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP"};

        for (String headerName : headerNames) {
            String ip = request.getHeader(headerName);
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                return ip.split(",")[0].trim();
            }
        }

        // 모든 헤더에서 IP를 찾지 못한 경우, 기본적으로 RemoteAddr을 사용
        return request.getRemoteAddr();
    }

    public boolean checkLiked(Long id) {
        Likey likey = likeyRepository.findById(id).orElse(null);
        return false;
    }
}
