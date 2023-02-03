package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.JoinReq;
import com.ssafy.a107.api.response.UserRes;
import com.ssafy.a107.common.exception.ConflictException;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.common.exception.TokenException;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;

import java.util.List;


public interface UserService {

    Long createUser(JoinReq joinReq);
    UserRes getUserBySeq(Long userSeq) throws NotFoundException;
    UserRes getUserByEmail(String email) throws NotFoundException;
    void checkEmailDuplicate(String email) throws ConflictException;
    List<UserRes> getFriends(Long userSeq) throws NotFoundException;

    List<UserRes> getBlockeds(Long userSeq) throws NotFoundException;
    HttpEntity<MultiValueMap<String, String>> generateAuthCodeRequest(String code, String state);
    String extractAccessToken(String tokenRes) throws TokenException;
    ResponseEntity<String> requestAccessToken(HttpEntity req);
    ResponseEntity<String> requestProfile(HttpEntity req);
    HttpEntity<MultiValueMap<String, String>> generateProfileRequest(String accessToken);
    String getNaverEmail(String accessToken);
    String getKakaoAccessToken(String code) throws TokenException;
    String getKakaoProfile(String token) throws NotFoundException;
    String getKakaoEmail(String accessToken) throws NotFoundException;
    void deleteUser(Long userSeq) throws NotFoundException;
}
