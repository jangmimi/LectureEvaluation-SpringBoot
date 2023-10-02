package com.springproject.service;

import com.springproject.model.User;
import com.springproject.repository.LeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class LeService {

    @Autowired
    private LeRepository leRepository;

    @Transactional
    public Long join(User user) {
        leRepository.save(user);
        return user.getUserNumber();
    }

    public User login(String uesrId, String userPw) {
        Optional<User> findUser = leRepository.findByUserId(uesrId);
        if(findUser.isPresent()) {
            if(userPw.equals(findUser.get().getUserPw())) {
                return findUser.orElse(null);
            }
        }
        return null;
    }

}
