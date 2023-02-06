package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.response.JoinRes;
import com.ssafy.a107.api.service.OAuthService;
import com.ssafy.a107.api.service.UserService;
import com.ssafy.a107.common.exception.ConflictException;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.common.exception.TokenException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "인증 API", tags = {"OAuth"})
@RestController
@RequestMapping("/api/oauth")
@RequiredArgsConstructor
@Slf4j
public class OAuthController {

    private final UserService userService;
    private final OAuthService oAuthService;

    @GetMapping("/naver")
    @ApiOperation(value = "네이버 OAuth", notes = "유저의 네이버 이메일 제공, 이미 가입한 유저면 null")
    public ResponseEntity<?> naverCallBack(@RequestParam String code, @RequestParam String state) throws TokenException, ConflictException {

        log.debug("Naver OAuth");
        log.debug("OAuth code: {}", code);
        log.debug("OAuth state: {}", state);

        String accessToken = oAuthService.extractAccessToken(oAuthService.requestAccessToken(oAuthService.generateAuthCodeRequest(code, state)).getBody());

        log.debug("accessToken: {}", accessToken);

        String userEmail = oAuthService.getNaverEmail(accessToken);

        userService.checkEmailDuplicate(userEmail);

        return ResponseEntity.status(HttpStatus.OK).body(new JoinRes(userEmail));
    }

    @GetMapping("/kakao")
    @ApiOperation(value = "카카오 OAuth", notes = "유저의 카카오 이메일 제공, 이미 가입한 유저면 null")
    public ResponseEntity<?> kakaoCallBack(@RequestParam String code) throws TokenException, ConflictException, NotFoundException {

        log.debug("Kakao OAuth");
        log.debug("OAuth code: {}", code);

        String accessToken = oAuthService.getKakaoAccessToken(code);

        log.debug("accessToken: {}", accessToken);

        String userEmail = oAuthService.getKakaoEmail(accessToken);

        userService.checkEmailDuplicate(userEmail);

        return ResponseEntity.status(HttpStatus.OK).body(new JoinRes(userEmail));
    }
}
