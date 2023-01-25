package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.JoinReq;
import com.ssafy.a107.db.entity.User;


public interface UserService {

    Long createUser(JoinReq joinReq);
    User getUserBySeq(Long userSeq);
    User getUserByEmail(String email);
}
