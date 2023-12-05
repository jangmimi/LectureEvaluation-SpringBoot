package com.springproject.service;

import com.springproject.model.Freeboard;
import com.springproject.repository.FreeboardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FreeboardService {

    @Autowired
    private FreeboardRepository freeboardRepository;

    public void write(Freeboard freeboard) {
        log.info("write 실행");
        freeboardRepository.save(freeboard);
    }
}
