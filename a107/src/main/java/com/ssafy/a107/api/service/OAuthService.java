package com.ssafy.a107.api.service;

import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.common.exception.TokenException;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public interface OAuthService {

    HttpEntity<MultiValueMap<String, String>> generateAuthCodeRequest(String code, String state);
    String extractAccessToken(String tokenRes) throws TokenException;
    ResponseEntity<String> requestAccessToken(HttpEntity req);
    ResponseEntity<String> requestProfile(HttpEntity req);
    HttpEntity<MultiValueMap<String, String>> generateProfileRequest(String accessToken);
    String getNaverEmail(String accessToken);
    String getKakaoAccessToken(String code) throws TokenException;
    String getKakaoProfile(String token) throws NotFoundException;
    String getKakaoEmail(String accessToken) throws NotFoundException;
}
