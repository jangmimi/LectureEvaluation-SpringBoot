package com.springproject.service;

import com.springproject.model.User;
import com.springproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class UserServiceTest {

    @MockBean
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @Test
    @Rollback(value = false)
    void 회원가입() {
        // given 데이터가 주어지면
        User user = new User(1L, "test", "1234", "test@test.com", "test@test.com", 0);

        // when 실행부
        userService.join(user);

        // then 검증부
        assertThat(user.getUserId()).isEqualTo("test");
        assertThat(user.getUserPw()).isEqualTo("1234");
        // UserRepository가 특정 값을 반환하도록 모킹(Mocking)
        when(userRepository.findByUserId("test")).thenReturn(Optional.empty());

        // UserService의 join 메서드 호출
        Long userNumber = userService.join(user);

        // 검증: 사용자 번호가 null이 아닌지 확인
        assertNotNull(userNumber);
    }

    @Test
    public void testDupli() throws Exception {
        // given
        User user1 = new User(1L, "test", "1234", "test@test.com", "test@test.com", 0);
        User user2 = new User(2L, "test", "0000", "test@test.com", "test@test.com", 0);

        // when
        userService.join(user1);

        // then
        // 검증 - 중복 사용자가 있을 때 예외가 발생하는지 확인
//        IllegalStateException e = assertThrows(IllegalStateException.class,
//                () -> userService.join(user2));
//        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원");
        new AssertThrows(Exception.class) {
            public void test() {
                new User().setUserPw(null);
            }
        }.runTest();
    }

//    @Test
//    void login() {
//        // given
//
//
//        // when
//
//        // then
//
//
//    }
}