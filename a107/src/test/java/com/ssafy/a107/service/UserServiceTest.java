package com.ssafy.a107.service;

import com.ssafy.a107.api.service.UserServiceImpl;
import com.ssafy.a107.db.entity.User;
import com.ssafy.a107.db.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServiceImpl userService;

    @Test
    @DisplayName("유저 조회 테스트")
    public void findUserBySeqTest() {
        User user = User.builder()
                .email("test@test.com")
                .password("pass")
                .city("seoul")
                .gender(true)
                .phoneNumber("01012341234")
                .name("홍길동")
                .profileImagePath("abc.jpg")
                .MBTI("ENTJ")
                .point(1000L)
                .build();

        User savedUser = userRepository.save(user);

        Assertions.assertEquals(savedUser, userService.getUserBySeq(savedUser.getSeq()));


    }
}
