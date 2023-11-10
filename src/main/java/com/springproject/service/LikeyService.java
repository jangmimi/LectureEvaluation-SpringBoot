package com.springproject.service;

import com.springproject.model.Likey;
import com.springproject.model.User;
import com.springproject.repository.LikeyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.Like;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
@Service
public class LikeyService {

    @Autowired
    private LikeyRepository likeyRepository;

    public Likey like(Long evaluationID, User user) {
        // 추천 여부를 먼저 체크
        Likey exist = checkLiked(evaluationID, user);

        if (exist == null) {
            Likey likey = new Likey();
            likey.setUser(user);
            likey.setEvaluationId(evaluationID);

            return likeyRepository.save(likey);
        } else {
            log.info("이미 추천된 강의평가입니다.");
            likeyRepository.delete(exist);
            return null;
        }
    }

    // 추천 여부 체크 메서드
    public Likey checkLiked(Long evaluationID, User user) {
        return likeyRepository.findByEvaluationIdAndUserUserNumber(evaluationID, user.getUserNumber());
    }
}
