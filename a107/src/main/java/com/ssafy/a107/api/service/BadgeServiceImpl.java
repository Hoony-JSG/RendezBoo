package com.ssafy.a107.api.service;

import com.ssafy.a107.db.entity.Badge;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BadgeServiceImpl implements BadgeService{

    @Override
    public List<Badge> getBadgeByUserSeq(Long userSeq) {
        return null;
    }
}
