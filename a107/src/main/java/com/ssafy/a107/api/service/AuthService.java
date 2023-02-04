package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.JoinReq;
import com.ssafy.a107.api.request.LoginReq;
import com.ssafy.a107.api.request.LogoutReq;
import com.ssafy.a107.api.response.TokenRes;
import com.ssafy.a107.common.exception.BadRequestException;
import com.ssafy.a107.common.exception.ConflictException;
import com.ssafy.a107.common.exception.JwtInvalidException;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.db.entity.RefreshToken;

public interface AuthService {

    Long createUser(JoinReq joinReq);
    TokenRes login(LoginReq loginReq) throws NotFoundException, ConflictException;
    TokenRes reissue(String bearerToken) throws JwtInvalidException;
    void logout(LogoutReq logoutReq) throws JwtInvalidException, BadRequestException;
    String resolveToken(String bearerToken);
    void checkPassword(String rawPassword, String findUserPassword) throws ConflictException;
    RefreshToken saveRefreshToken(String userEmail);
    boolean validateToken(String accessToken, String userEmail) throws JwtInvalidException;
    String getEmailFromToken(String token) throws JwtInvalidException;
}
