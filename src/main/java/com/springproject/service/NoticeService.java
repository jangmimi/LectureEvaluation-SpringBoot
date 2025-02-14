package com.springproject.service;

import com.springproject.model.Freeboard;
import com.springproject.model.User;
import com.springproject.repository.FreeboardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class NoticeService {

    @Autowired
    private FreeboardRepository freeboardRepository;

    @Transactional
    public Freeboard write(String title, String content, User user) {
        Freeboard freeboard = new Freeboard();
        freeboard.setTitle(title);
        freeboard.setContent(content);
        freeboard.setUser(user);
        log.info(freeboard.toString());
        return freeboardRepository.save(freeboard);
    }

    public List<Freeboard> getList() {
        return freeboardRepository.findAll();
    }
}
