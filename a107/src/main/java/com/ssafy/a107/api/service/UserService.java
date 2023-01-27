package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.JoinReq;
import com.ssafy.a107.api.response.UserRes;
import com.ssafy.a107.common.exception.NotFoundException;

import java.util.List;


public interface UserService {

    Long createUser(JoinReq joinReq);
    UserRes getUserBySeq(Long userSeq) throws NotFoundException;
    UserRes getUserByEmail(String email) throws NotFoundException;
    Boolean checkEmailDuplicate(String email);
    List<UserRes> getFriends(Long userSeq) throws NotFoundException;

    List<UserRes> getBlockeds(Long userSeq) throws NotFoundException;
}
