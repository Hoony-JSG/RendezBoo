package com.ssafy.a107.api.service;

import com.ssafy.a107.db.entity.Badge;
import java.util.List;

public interface BadgeService {
    List<Badge> getBadgeByUserSeq(Long userSeq);
}
