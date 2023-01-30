package com.ssafy.a107.api.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ssafy.a107.api.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
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
public class OAuthController {

    private static PasswordEncoder passwordEncoder;
    private static UserService userService;
    private static Logger log;

    @GetMapping("/naver")
    public ResponseEntity<?> naverCallBack(@RequestParam String code, @RequestParam String state) throws Exception {

        log.info("OAuth code: {}", code);
        log.info("OAuth state: {}", state);

        try {
            // Access Token
            String accessToken = userService.extractAccessToken(userService.requestAccessToken(userService.generateAuthCodeRequest(code, state)).getBody());

            // User Profile
            ResponseEntity<String> profile = userService.requestProfile(userService.generateProfileRequest(accessToken));

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(profile.getBody());
            JsonElement userInfo = element.getAsJsonObject().get("response");

            log.info("userInfo: {}", userInfo);

            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
