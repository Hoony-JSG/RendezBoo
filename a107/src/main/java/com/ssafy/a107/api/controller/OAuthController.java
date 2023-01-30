package com.ssafy.a107.api.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ssafy.a107.api.response.JoinRes;
import com.ssafy.a107.api.service.UserService;
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

@Api(value = "인증 API")
@RestController
@RequestMapping("/api/oauth")
@RequiredArgsConstructor
@Slf4j
public class OAuthController {

    private final UserService userService;

    @GetMapping("/naver")
    @ApiOperation(value = "네이버 OAuth", notes = "유저의 네이버 이메일 제공, 이미 가입한 유저면 null")
    public ResponseEntity<?> naverCallBack(@RequestParam String code, @RequestParam String state) throws Exception {

        log.debug("Naver OAuth");
        log.debug("OAuth code: {}", code);
        log.debug("OAuth state: {}", state);

        try {
            // Access Token
            String accessToken = userService.extractAccessToken(userService.requestAccessToken(userService.generateAuthCodeRequest(code, state)).getBody());

            log.debug("accessToken: {}", accessToken);

            // User Profile
            ResponseEntity<String> profile = userService.requestProfile(userService.generateProfileRequest(accessToken));

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(profile.getBody());
            JsonElement userInfo = element.getAsJsonObject().get("response");
            String userEmail = userInfo.getAsJsonObject().get("email").toString().replaceAll("\"", "");

            log.debug("userInfo: {}", userInfo);
            log.debug("userEmail: {}", userEmail);

            Boolean dupCheck = userService.checkEmailDuplicate(userEmail);

            if(!dupCheck) {
                // 회원 등록 - JoinRes 보내고 추가 정보 입력 받음
                return ResponseEntity.status(HttpStatus.OK).body(new JoinRes(userEmail));
            }
            else {
                // 이미 등록된 유저
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/kakao")
    @ApiOperation(value = "카카오 OAuth", notes = "유저의 카카오 이메일 제공, 이미 가입한 유저면 null")
    public ResponseEntity<?> kakaoCallBack(@RequestParam String code) throws Exception {

        log.debug("Kakao OAuth");
        log.debug("OAuth code: {}", code);

        try {
            String accessToken = userService.getKakaoAccessToken(code);

            log.debug("accessToken: {}", accessToken);

            String profile = userService.getKakaoProfile(accessToken);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(profile);
            JsonElement kakaoAccount = element.getAsJsonObject().get("kakao_account");
            String userEmail = kakaoAccount.getAsJsonObject().get("email").toString().replaceAll("\"", "");

            log.debug("userEmail: {}", userEmail);

            Boolean dupCheck = userService.checkEmailDuplicate(userEmail);

            if(!dupCheck) {
                // 회원 등록 - JoinRes 보내고 추가 정보 입력 받음
                return ResponseEntity.status(HttpStatus.OK).body(new JoinRes(userEmail));
            }
            else {
                // 이미 등록된 유저
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
