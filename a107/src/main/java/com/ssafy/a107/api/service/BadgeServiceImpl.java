package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.BadgeCreateReq;
import com.ssafy.a107.api.request.BadgeUpdateReq;
import com.ssafy.a107.api.request.UserBadgeReq;
import com.ssafy.a107.api.response.BadgeRes;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.common.util.S3Uploader;
import com.ssafy.a107.db.entity.Badge;
import com.ssafy.a107.db.entity.UserBadge;
import com.ssafy.a107.db.repository.BadgeRepository;
import com.ssafy.a107.db.repository.UserBadgeRepository;
import com.ssafy.a107.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BadgeServiceImpl implements BadgeService {

    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;
    private final UserBadgeRepository userBadgeRepository;
    private final S3Uploader s3Uploader;


    @Override
    public List<BadgeRes> getBadgeByUserSeq(Long userSeq) throws NotFoundException{
        if(!userRepository.existsById(userSeq)) throw new NotFoundException("Wrong User Seq!");
        return badgeRepository.findBadgesByUserSeq(userSeq).stream()
                .map(BadgeRes::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
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
    public List<BadgeRes> getAllBadges() {
        return badgeRepository.findAll().stream()
                .map(BadgeRes::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long createBadge(BadgeCreateReq badgeCreateReq) throws IOException {
        String url = s3Uploader.upload(badgeCreateReq.getImage(), "images");
        Badge badge = Badge.builder()
                .name(badgeCreateReq.getName())
                .url(url)
                .build();
        badgeRepository.save(badge);
        return badge.getSeq();
    }

    @Override
    @Transactional
    public Long updateBadge(BadgeUpdateReq badgeUpdateReq) throws NotFoundException, IOException {
        String url = s3Uploader.upload(badgeUpdateReq.getImage(), "images");
        Badge badge = badgeRepository.findById(badgeUpdateReq.getSeq())
                .orElseThrow(() -> new NotFoundException("Wrong Badge Seq"));
        badge.update(badgeUpdateReq.getName(), url);
        badgeRepository.save(badge);
        return badge.getSeq();
    }

    @Override
    public void deleteBadge(Long seq) throws NotFoundException{
        if(badgeRepository.existsById(seq)){
            badgeRepository.deleteById(seq);
        }else throw new NotFoundException("Invalid badge sequence!");
    }


}
