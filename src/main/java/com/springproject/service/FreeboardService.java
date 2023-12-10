package com.springproject.service;

import com.springproject.model.Freeboard;
import com.springproject.model.User;
import com.springproject.repository.FreeboardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class FreeboardService {

    @Autowired
    private FreeboardRepository freeboardRepository;

    @Transactional
    public Freeboard write(String title, String content, User user) {
        log.info("write 실행");
        Freeboard freeboard = new Freeboard();
        freeboard.setTitle(title);
        freeboard.setContent(content);
        freeboard.setUser(user);
        log.info(freeboard.toString());
        return freeboardRepository.save(freeboard);
    }
}
