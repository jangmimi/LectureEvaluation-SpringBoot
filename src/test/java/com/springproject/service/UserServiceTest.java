package com.springproject.service;

import com.springproject.model.User;
import com.springproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class UserServiceTest {

    @MockBean
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @Test
    void 회원가입() {
        // given 데이터가 주어지면
        User user = new User(1L, "test", "1234", "test@test.com", "test@test.com", 0, null);

        // when 실행부
        userService.validateDuplicateUser(user);
        userService.join(user);
        // then 검증부
        assertThat(user.getUserId()).isEqualTo("test");
        assertThat(user.getUserPw()).isEqualTo("1234");
    }

    @Test
    public void 중복체크() {
        // given
        User user1 = new User(1L, "test", "1234", "test@test.com", "test@test.com", 0, null);
        User user2 = new User(2L, "test", "0000", "test@test.com", "test@test.com", 0, null);

        // when
        userService.join(user1);
        Long result = userService.join(user2);

        // then
        // 검증 - 중복 사용자가 있을 때 예외가 발생하는지 확인
        assertThat(result).isEqualTo(0);
    }

    @Test
    void login() {
        // given
        User user1 = new User(1L, "test", "1234", "test@test.com", "test@test.com", 0, null);

        // when
        userService.validateDuplicateUser(user1);
        userService.join(user1);
        User logined = userService.login(user1.getUserId(), user1.getUserPw());

        // then
        assertThat(user1.getUserNumber()).isEqualTo(logined.getUserNumber());

    }
}