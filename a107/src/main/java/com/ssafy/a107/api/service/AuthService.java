package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.JoinReq;
import com.ssafy.a107.api.request.LoginReq;
import com.ssafy.a107.api.response.TokenRes;
import com.ssafy.a107.common.exception.BadRequestException;
import com.ssafy.a107.common.exception.ConflictException;
import com.ssafy.a107.common.exception.JwtInvalidException;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.db.entity.RefreshToken;

import java.text.ParseException;

public interface AuthService {

    Long createUser(JoinReq joinReq) throws ParseException;
    TokenRes login(LoginReq loginReq) throws NotFoundException, ConflictException;
    TokenRes reissue(String bearerToken) throws JwtInvalidException, ConflictException, NotFoundException;
    String logout(String bearerToken) throws JwtInvalidException, BadRequestException, ConflictException, NotFoundException;
    String resolveToken(String bearerToken);
    void checkPassword(String rawPassword, String findUserPassword) throws ConflictException;
    RefreshToken saveRefreshToken(String userEmail);
    boolean validateToken(String accessToken) throws JwtInvalidException;
    String getEmailFromToken(String token) throws JwtInvalidException;
}
