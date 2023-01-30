package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.BadgeCreateReq;
import com.ssafy.a107.api.request.BadgeUpdateReq;
import com.ssafy.a107.api.request.UserBadgeReq;
import com.ssafy.a107.api.response.BadgeRes;
import com.ssafy.a107.common.exception.NotFoundException;

import java.io.IOException;
import java.util.List;

public interface BadgeService {
    List<BadgeRes> getBadgeByUserSeq(Long userSeq) throws NotFoundException;

    void createUserBadge(UserBadgeReq userBadgeReq) throws NotFoundException;

    List<BadgeRes> getAllBadges();

    Long createBadge(BadgeCreateReq badgeCreateReq) throws IOException;

    Long updateBadge(BadgeUpdateReq badgeUpdateReq) throws NotFoundException, IOException;

    void deleteBadge(Long seq) throws NotFoundException;

}
