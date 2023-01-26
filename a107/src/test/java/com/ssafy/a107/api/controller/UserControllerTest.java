package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.request.JoinReq;
import com.ssafy.a107.api.request.LoginReq;
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
class UserControllerTest {

    @Autowired
    UserController userController;

    @Test
    public void loginSuccessTest() throws Exception {
        //given
        JoinReq joinReq = JoinReq.builder()
                .email("test@ssafy.com")
                .password("test123")
                .build();

        LoginReq loginReq = LoginReq.builder()
                .email("test@ssafy.com")
                .password("test123")
                .build();

        //when
        userController.joinUser(joinReq);
        ResponseEntity<?> responseEntity = userController.login(loginReq);

        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}