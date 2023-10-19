package com.springproject.repository;

import com.springproject.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @MockBean
    UserRepository userRepository;

    @Test
    void 회원가입() {
//        User user = new User(1L, "test", "1234", "test@test.com", "test@test.com", 0);
        User user = new User();
        user.setUserId("test");
        userRepository.save(user);

        User find = userRepository.findByUserId(user.getUserId()).get();
        assertThat(find).isEqualTo(user);
    }
}