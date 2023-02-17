package com.ssafy.a107.service;

import com.ssafy.a107.api.request.JoinReq;
import com.ssafy.a107.api.service.AuthService;
import com.ssafy.a107.api.service.UserServiceImpl;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.db.entity.User;
import com.ssafy.a107.db.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    AuthService authService;

    @Test
    @DisplayName("유저 조회 테스트")
    public void findUserBySeqTest() throws NotFoundException {
        User user = User.builder()
                .email("test@test.com")
                .password("pass")
                .city("seoul")
                .gender(true)
                .phoneNumber("01012341234")
                .name("홍길동")
                .profileImagePath("abc.jpg")
                .mbti("ENTJ")
                .point(1000L)
                .build();

        User savedUser = userRepository.save(user);

        Assertions.assertEquals(savedUser, userService.getUserBySeq(savedUser.getSeq()));
    }
    
    @Test
    @DisplayName("유저 회원가입 테스트")
    public void createUserTest() throws Exception {
        //given
        JoinReq joinReq = JoinReq.builder()
                .email("test@test.com")
                .password("pass")
                .city("seoul")
                .gender(true)
                .phoneNumber("01012341234")
                .name("홍길동")
                .profileImagePath("abc.jpg")
                .mbti("ENTJ")
                .build();

        //when
        Long savedUser = authService.createUser(joinReq);
        
        //then
        Assertions.assertEquals(userService.getUserBySeq(savedUser).getEmail(), joinReq.getEmail());
        Assertions.assertEquals(userService.getUserBySeq(savedUser).getPoint(), 0);

//        System.out.println("비밀번호 테스트: " + userService.getUserBySeq(savedUser).getPassword());
    }
}
