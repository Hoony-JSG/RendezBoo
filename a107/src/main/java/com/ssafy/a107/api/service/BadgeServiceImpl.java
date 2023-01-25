package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.BadgeCreateReq;
import com.ssafy.a107.api.request.BadgeUpdateReq;
import com.ssafy.a107.api.request.UserBadgeReq;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.db.entity.Badge;
import com.ssafy.a107.db.entity.UserBadge;
import com.ssafy.a107.db.repository.BadgeRepository;
import com.ssafy.a107.db.repository.UserBadgeRepository;
import com.ssafy.a107.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BadgeServiceImpl implements BadgeService{

    UserRepository userRepository;
    BadgeRepository badgeRepository;
    UserBadgeRepository userBadgeRepository;

    @Autowired
    public BadgeServiceImpl(UserRepository userRepository, BadgeRepository badgeRepository, UserBadgeRepository userBadgeRepository) {
        this.userRepository = userRepository;
        this.badgeRepository = badgeRepository;
        this.userBadgeRepository = userBadgeRepository;
    }

    @Override
    public List<Badge> getBadgeByUserSeq(Long userSeq) {
        return badgeRepository.findBadgesByUserSeq(userSeq);
    }

    @Override
    public void createUserBadge(UserBadgeReq userBadgeReq) throws NotFoundException {
        UserBadge userBadge = UserBadge.builder()
                .user(userRepository.findById(userBadgeReq.getUserSeq())
                        .orElseThrow(() -> new NotFoundException("Wrong User Seq!")))
                .badge(badgeRepository.findById(userBadgeReq.getBadgeSeq())
                        .orElseThrow(() -> new NotFoundException("Wrong Badge Seq!")))
                .build();
        userBadgeRepository.save(userBadge);
    }

    @Override
    public List<Badge> getAllBadges() {
        return badgeRepository.findAll();
    }

    @Override
    public Long createBadge(BadgeCreateReq badgeCreateReq) {
        Badge badge = new Badge().builder()
                .name(badgeCreateReq.getName())
                .url(badgeCreateReq.getUrl())
                .build();
        badgeRepository.save(badge);
        return badge.getSeq();
    }

    @Override
    public Long updateBadge(BadgeUpdateReq badgeUpdateReq) throws NotFoundException {
        Badge badge = badgeRepository.findById(badgeUpdateReq.getSeq())
                .orElseThrow(() -> new NotFoundException("Wrong Badge Seq"));
        badge.setName(badgeUpdateReq.getName());
        badge.setUrl(badgeUpdateReq.getUrl());
        badgeRepository.save(badge);
        return badge.getSeq();
    }

    @Override
    public void deleteBadge(Long seq) {
        badgeRepository.deleteById(seq);
    }


}
