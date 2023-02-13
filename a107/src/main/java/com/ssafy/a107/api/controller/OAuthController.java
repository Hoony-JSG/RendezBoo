package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.response.JoinRes;
import com.ssafy.a107.api.response.TokenRes;
import com.ssafy.a107.api.service.AuthService;
import com.ssafy.a107.api.service.OAuthService;
import com.ssafy.a107.common.exception.ConflictException;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.common.exception.TokenException;
import com.ssafy.a107.db.repository.UserRepository;
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

    private final UserRepository userRepository;
    private final OAuthService oAuthService;
    private final AuthService authService;

    @GetMapping("/naver")
    @ApiOperation(value = "네이버 OAuth", notes = "회원가입이 필요한 유저일 경우 이메일, 이미 가입한 유저일 경우 토큰 반환")
    public ResponseEntity<?> naverCallBack(@RequestParam String code, @RequestParam String state) throws TokenException, ConflictException, NotFoundException {

        log.debug("Naver OAuth");
        log.debug("OAuth code: {}", code);
        log.debug("OAuth state: {}", state);

        String accessToken = oAuthService.extractAccessToken(oAuthService.requestAccessToken(oAuthService.generateAuthCodeRequest(code, state)).getBody());

        log.debug("accessToken: {}", accessToken);

        String userEmail = oAuthService.getNaverEmail(accessToken);

        // 이미 가입한 유저
        if(userRepository.existsByEmail(userEmail)) {
            TokenRes tokenRes = authService.login(userEmail);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(tokenRes);
        }
        // 회원가입이 필요한 유저
        else {
            log.debug("회원가입이 필요한 email: {}", userEmail);
            return ResponseEntity.status(HttpStatus.OK).body(new JoinRes(userEmail, 0));
        }
    }

    @GetMapping("/kakao")
    @ApiOperation(value = "카카오 OAuth", notes = "회원가입이 필요한 유저일 경우 이메일, 이미 가입한 유저일 경우 토큰 반환")
    public ResponseEntity<?> kakaoCallBack(@RequestParam String code) throws TokenException, ConflictException, NotFoundException {

        log.debug("Kakao OAuth");
        log.debug("OAuth code: {}", code);

        String accessToken = oAuthService.getKakaoAccessToken(code);

        log.debug("accessToken: {}", accessToken);

        String userEmail = oAuthService.getKakaoEmail(accessToken);

        // 이미 가입한 유저
        if(userRepository.existsByEmail(userEmail)) {
            TokenRes tokenRes = authService.login(userEmail);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(tokenRes);
        }
        // 회원가입이 필요한 유저
        else {
            log.debug("회원가입이 필요한 email: {}", userEmail);
            return ResponseEntity.status(HttpStatus.OK).body(new JoinRes(userEmail, 1));
        }
    }
}
