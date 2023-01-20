package com.ssafy.a107.api.service;

import com.ssafy.a107.db.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    
    @Override
    public User getUserBySeq(Long userSeq) {
        // TODO: JPA 뉴비 이슈로 보류
        return null;
    }
}
