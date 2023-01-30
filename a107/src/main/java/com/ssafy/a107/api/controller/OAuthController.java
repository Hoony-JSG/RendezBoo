package com.ssafy.a107.api.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ssafy.a107.api.response.JoinRes;
import com.ssafy.a107.api.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public ResponseEntity<?> naverCallBack(@RequestParam String code, @RequestParam String state) throws Exception {

        log.debug("OAuth code: {}", code);
        log.debug("OAuth state: {}", state);

        try {
            // Access Token
            String accessToken = userService.extractAccessToken(userService.requestAccessToken(userService.generateAuthCodeRequest(code, state)).getBody());

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
                JoinRes joinRes = JoinRes.builder()
                        .gender(userInfo.getAsJsonObject().get("gender").toString().equals("\"M\"") ? true : false)
                        .phoneNumber(userInfo.getAsJsonObject().get("mobile").toString().replaceAll("\"", ""))
                        .name(userInfo.getAsJsonObject().get("name").toString().replaceAll("\"", ""))
                        .email(userEmail)
                        .build();

                return ResponseEntity.status(HttpStatus.OK).body(joinRes);
            }
            else {
                // 이미 등록된 유저
                return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByEmail(userEmail));
            }
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
