package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.service.UserServiceImpl;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.db.entity.User;
import com.ssafy.a107.db.repository.UserFriendRepository;
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
class UserFriendControllerTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserFriendRepository userFriendRepository;

    @Autowired
    UserFriendController userFriendController;

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
                .mbti("ENTJ")
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
                .mbti("ENTJ")
                .point(1000L)
                .build();

        User other = userRepository.save(userOther);

        try{
            ResponseEntity<?> responseEntity = userFriendController.addFriend(me.getSeq(), other.getSeq());
            System.out.println(responseEntity.getStatusCode());
            Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        }catch (NotFoundException ex){
            System.out.println("Not Found Exception");
        }
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
                .mbti("ENTJ")
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
                .mbti("ENTJ")
                .point(1000L)
                .build();

        User other = userRepository.save(userOther);

        //Exception처리를 Exception handler로 했으므로 이 테스트코드가 에러가 난다.
        try{
            ResponseEntity<?> addFriend = userFriendController.addFriend(me.getSeq(), other.getSeq());
            ResponseEntity<?> deleteFriend = userFriendController.deleteFriend(me.getSeq(), other.getSeq());
            Assertions.assertThat(deleteFriend.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        }catch (NotFoundException ex){
            System.out.println("Not Found Exception");
        }
        //



    }
}