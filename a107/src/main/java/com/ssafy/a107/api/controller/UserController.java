package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.request.JoinReq;
import com.ssafy.a107.api.request.LoginReq;
import com.ssafy.a107.api.response.UserRes;
import com.ssafy.a107.api.service.UserService;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.db.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Optional;

@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    // 유저 정보 조회
    @GetMapping("/{userSeq}")
    @ApiOperation(value = "유저 정보 조회", notes = "Seq로 유저 정보 제공")
    public ResponseEntity<?> getUserInfo(@PathVariable Long userSeq) {
        try {
            UserRes user = userService.getUserBySeq(userSeq);

            if(user != null) {
                return ResponseEntity.status(HttpStatus.OK).body(user);
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // 회원가입
    @PostMapping("/join")
    @ApiOperation(value = "유저 회원가입", notes = "유저 회원가입")
    public ResponseEntity<?> joinUser(@RequestBody JoinReq joinReq) throws NotFoundException {
        UserRes findUser = userService.getUserByEmail(joinReq.getEmail());

        if(findUser == null) {
            userService.createUser(joinReq);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/login")
    @ApiOperation(value = "유저 로그인")
    public ResponseEntity<?> login(@RequestBody LoginReq loginReq) throws NotFoundException {
        UserRes findUser = userService.getUserByEmail(loginReq.getEmail());

        // 비밀번호가 일치하면
        if(findUser != null && passwordEncoder.matches(loginReq.getPassword(), findUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
