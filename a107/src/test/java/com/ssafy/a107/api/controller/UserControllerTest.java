package com.ssafy.a107.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.a107.api.request.JoinReq;
import com.ssafy.a107.api.request.LoginReq;
import com.ssafy.a107.api.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;

    @Test
    public void loginSuccessTest() throws Exception {
        //given
        JoinReq joinReq = JoinReq.builder()
                .email("test@ssafy.com")
                .password("test123")
                .city("Seoul")
                .gender(true)
                .phoneNumber("010-1234-1234")
                .name("김싸피")
                .profileImagePath("test.jpg")
                .mbti("INTP")
                .build();

        LoginReq loginReq = LoginReq.builder()
                .email("test@ssafy.com")
                .password("test123")
                .build();

        //when
        userService.createUser(joinReq);

        //then
        mvc.perform(
                MockMvcRequestBuilders.post("/api/user/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginReq))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    public void loginFailTest() throws Exception {
        //given
        JoinReq joinReq = JoinReq.builder()
                .email("test@ssafy.com")
                .password("test123")
                .city("Seoul")
                .gender(true)
                .phoneNumber("010-1234-1234")
                .name("김싸피")
                .profileImagePath("test.jpg")
                .mbti("INTP")
                .build();

        LoginReq loginReq = LoginReq.builder()
                .email("test@ssafy.com")
                .password("test123123")
                .build();

        //when
        userService.createUser(joinReq);

        //then
        mvc.perform(
                        MockMvcRequestBuilders.post("/api/user/login")
                                .accept(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(loginReq))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isConflict());
    }

    @Test
    public void checkEmailConflictTest() throws Exception {
        //given
        JoinReq joinReq = JoinReq.builder()
                .email("test@ssafy.com")
                .password("test123")
                .city("Seoul")
                .gender(true)
                .phoneNumber("010-1234-1234")
                .name("김싸피")
                .profileImagePath("test.jpg")
                .mbti("INTP")
                .build();

        //when
        userService.createUser(joinReq);

        //then
        mvc.perform(MockMvcRequestBuilders.get("/api/user/check/test@ssafy.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isConflict());
    }

    @Test
    public void checkEmailOKTest() throws Exception {
        //given
        JoinReq joinReq = JoinReq.builder()
                .email("test@ssafy.com")
                .password("test123")
                .city("Seoul")
                .gender(true)
                .phoneNumber("010-1234-1234")
                .name("김싸피")
                .profileImagePath("test.jpg")
                .mbti("INTP")
                .build();

        //when
        userService.createUser(joinReq);

        //then
        mvc.perform(MockMvcRequestBuilders.get("/api/user/check/ssafykim@ssafy.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }
}