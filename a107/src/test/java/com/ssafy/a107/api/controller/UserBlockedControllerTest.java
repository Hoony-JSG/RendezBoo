package com.ssafy.a107.api.controller;

import com.ssafy.a107.db.entity.User;
import com.ssafy.a107.db.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserBlockedControllerTest {

    @Autowired
    UserBlockedController userBlockedController;

    @Autowired
    UserRepository userRepository;

    @Test
    void createBlockedUser() {

        User userMe = User.builder()
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

        User me = userRepository.save(userMe);

        User userOther = User.builder()
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

        User other = userRepository.save(userOther);

        ResponseEntity<?> responseEntity = userBlockedController.createBlockedUser(me.getSeq(), other.getSeq());

//        System.out.println(responseEntity);
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}