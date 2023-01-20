package com.ssafy.a107.api.service;

import com.ssafy.a107.db.entity.User;

public interface UserService {

    User getUserBySeq(Long userSeq);
}
