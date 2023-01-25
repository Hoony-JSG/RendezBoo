package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.service.UserServiceImpl;
import com.ssafy.a107.db.entity.User;
import com.ssafy.a107.db.repository.FriendRepository;
import com.ssafy.a107.db.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FriendControllerTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    FriendRepository friendRepository;

    @Autowired
    FriendController friendController;

    @Test
    void addFriend() {
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

        ResponseEntity<?> responseEntity = friendController.addFriend(me.getSeq(), other.getSeq());

        System.out.println(responseEntity.getStatusCode());
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void deleteFriend() {
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

        ResponseEntity<?> addFriend = friendController.addFriend(me.getSeq(), other.getSeq());

//        System.out.println(addFriend);

        ResponseEntity<?> deleteFriend = friendController.deleteFriend(me.getSeq(), other.getSeq());

//        System.out.println(deleteFriend);
        Assertions.assertThat(deleteFriend.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }
}