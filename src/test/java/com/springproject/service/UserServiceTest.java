package com.springproject.service;

import com.springproject.model.User;
import com.springproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class UserServiceTest {

    @MockBean
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @Test
    void 회원가입() {
        // given 데이터가 주어지면
        User user = new User(1L, "test", "1234", "test@test.com", "test@test.com", 0);

        // when 실행부
        Long userNumber = userService.join(user);

        // then 검증부
        assertThat(user.getUserId()).isEqualTo("test");
    }

    @Test
    void 중복회원검증() {
        // given
        User user1 = new User();
        user1.setUserId("springboot");

        User user2 = new User();
        user2.setUserId("springboot");

        // when
        userService.join(user1);

        // then
        try {
            userService.join(user2);
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }

    }

    @Test
    void login() {
    }
}