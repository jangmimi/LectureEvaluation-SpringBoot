package com.springproject.service;

import com.springproject.model.User;
import com.springproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        userService.join(user);

        // then 검증부
        assertThat(user.getUserId()).isEqualTo("test");
        assertThat(user.getUserPw()).isEqualTo("1234");
    }

    @Test
    void 중복회원검증() {
        // given
        User user1 = new User(1L, "test", "1234", "test@test.com", "test@test.com", 0);
        User user2 = new User(2L, "test", "1234", "test@test.com", "test@test.com", 0);

        // when
        userRepository.save(user1);

        // then
        try {
            userService.join(user2);
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존");
        }
//        assertThrows(IllegalStateException.class,
//                () -> userService.join(user2));
    }

    @Test
    void login() {
        // given


        // when

        // then


    }
}