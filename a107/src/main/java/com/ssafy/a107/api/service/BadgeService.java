package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.BadgeCreateReq;
import com.ssafy.a107.api.request.BadgeUpdateReq;
import com.ssafy.a107.api.request.UserBadgeReq;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.db.entity.Badge;
import java.util.List;

public interface BadgeService {
    List<Badge> getBadgeByUserSeq(Long userSeq);

    void createUserBadge(UserBadgeReq userBadgeReq) throws NotFoundException;

    List<Badge> getAllBadges();

    Long createBadge(BadgeCreateReq badgeCreateReq);

    Long updateBadge(BadgeUpdateReq badgeUpdateReq) throws NotFoundException;

    void deleteBadge(Long seq);

}
