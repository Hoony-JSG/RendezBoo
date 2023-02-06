package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.request.JoinReq;
import com.ssafy.a107.api.request.LoginReq;
import com.ssafy.a107.api.response.TokenRes;
import com.ssafy.a107.api.response.UserRes;
import com.ssafy.a107.api.service.AuthService;
import com.ssafy.a107.api.service.UserService;
import com.ssafy.a107.common.exception.BadRequestException;
import com.ssafy.a107.common.exception.ConflictException;
import com.ssafy.a107.common.exception.JwtInvalidException;
import com.ssafy.a107.common.exception.NotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    // 유저 정보 조회
    @GetMapping("/{userSeq}")
    @ApiOperation(value = "유저 정보 조회", notes = "Seq로 유저 정보 제공")
    public ResponseEntity<?> getUserInfo(@PathVariable Long userSeq) throws NotFoundException{
        UserRes user = userService.getUserBySeq(userSeq);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    // 회원가입
    @PostMapping("/join")
    @ApiOperation(value = "유저 회원가입", notes = "유저 회원가입")
    public ResponseEntity<?> joinUser(@RequestBody JoinReq joinReq) throws ConflictException {
        userService.checkEmailDuplicate(joinReq.getEmail());
        userService.checkPhoneNumberDuplicate(joinReq.getPhoneNumber());

        authService.createUser(joinReq);

        return ResponseEntity.status(HttpStatus.CREATED).body(joinReq.getEmail());
    }

    // 로그인
    @PostMapping("/login")
    @ApiOperation(value = "유저 로그인", notes = "유저 로그인")
    public ResponseEntity<?> login(@RequestBody LoginReq loginReq) throws NotFoundException, ConflictException {
        TokenRes res = authService.login(loginReq);

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/reissue")
    @ApiOperation(value = "JWT 토큰 재발급", notes = "Bearer 리프레시 토큰 필요")
    public ResponseEntity<?> reissue(@RequestHeader("Authorization") String bearerToken) throws JwtInvalidException {
        TokenRes res = authService.reissue(bearerToken);

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/logout")
    @ApiOperation(value = "유저 로그아웃", notes = "Bearer 엑세스 토큰 필요")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String bearerToken) throws JwtInvalidException, BadRequestException {
        String userEmail = authService.logout(bearerToken);

        return ResponseEntity.status(HttpStatus.OK).body(userEmail);
    }

    // 아이디 중복체크
    @GetMapping("/check/{email}")
    @ApiOperation(value = "이메일 중복체크", notes = "이메일 중복 체크")
    public ResponseEntity<?> checkEmail(@PathVariable String email) throws ConflictException {
        userService.checkEmailDuplicate(email);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{userSeq}/friends")
    @ApiOperation(value="유저의 친구 목록", notes = "유저의 친구 목록")
    public ResponseEntity<?> getFriends(@PathVariable Long userSeq) throws NotFoundException{
        return ResponseEntity.status(HttpStatus.OK).body(userService.getFriends(userSeq));
    }
    @GetMapping("/{userSeq}/blockeds")
    @ApiOperation(value="유저의 친구 목록", notes = "유저의 친구 목록")
    public ResponseEntity<?> getBlockeds(@PathVariable Long userSeq) throws NotFoundException{
        return ResponseEntity.status(HttpStatus.OK).body(userService.getBlockeds(userSeq));
    }
    @DeleteMapping("/{userSeq}")
    @ApiOperation(value = "회원탈퇴", notes = "회원탈퇴")
    public ResponseEntity<?> deleteUser(@PathVariable Long userSeq) throws NotFoundException{
        userService.deleteUser(userSeq);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
