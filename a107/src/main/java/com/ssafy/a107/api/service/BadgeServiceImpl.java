package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.*;
import com.ssafy.a107.api.response.BadgeCheckRes;
import com.ssafy.a107.api.response.BadgeRes;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.common.util.S3Uploader;
import com.ssafy.a107.db.entity.Badge;
import com.ssafy.a107.db.entity.BadgeCondition;
import com.ssafy.a107.db.entity.User;
import com.ssafy.a107.db.entity.UserBadge;
import com.ssafy.a107.db.repository.BadgeConditionRepository;
import com.ssafy.a107.db.repository.BadgeRepository;
import com.ssafy.a107.db.repository.UserBadgeRepository;
import com.ssafy.a107.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BadgeServiceImpl implements BadgeService {

    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;
    private final UserBadgeRepository userBadgeRepository;
    private final BadgeConditionRepository badgeConditionRepository;
    private final UserService userService;
    private final S3Uploader s3Uploader;

    @Override
    @Transactional(readOnly = true)
    public List<BadgeRes> getBadgeByUserSeq(Long userSeq) throws NotFoundException{
        if(!userRepository.existsById(userSeq)) throw new NotFoundException("Wrong User Seq!");
        return badgeRepository.findBadgesByUserSeq(userSeq).stream()
                .map(BadgeRes::new)
                .collect(Collectors.toList());
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
    @Transactional(readOnly = true)
    public List<BadgeRes> getAllBadges() {
        return badgeRepository.findAll().stream()
                .map(BadgeRes::new)
                .collect(Collectors.toList());
    }

    @Override
    public Long createBadge(BadgeCreateReq badgeCreateReq) throws IOException {
        String url = s3Uploader.upload(badgeCreateReq.getImage(), "images");
        Badge badge = Badge.builder()
                .name(badgeCreateReq.getName())
                .description(badgeCreateReq.getDescription())
                .url(url)
                .build();
        badgeRepository.save(badge);
        return badge.getSeq();
    }

    @Override
    public Long updateBadge(BadgeUpdateReq badgeUpdateReq) throws NotFoundException, IOException {
        String url = s3Uploader.upload(badgeUpdateReq.getImage(), "images");
        Badge badge = badgeRepository.findById(badgeUpdateReq.getSeq())
                .orElseThrow(() -> new NotFoundException("Wrong Badge Seq"));
        badge.update(badgeUpdateReq.getName(), url, badgeUpdateReq.getDescription());
        badgeRepository.save(badge);
        return badge.getSeq();
    }

    @Override
    public void deleteBadge(Long seq) throws NotFoundException{
        if(badgeRepository.existsById(seq)){
            badgeRepository.deleteById(seq);
        }else throw new NotFoundException("Invalid badge sequence!");
    }

    @Transactional(readOnly = true)
    public BadgeRes getBadgeBySeq(Long badgeSeq) throws NotFoundException {
        Badge badge = badgeRepository.findById(badgeSeq)
                .orElseThrow(() -> new NotFoundException("Wrong badge seq!"));

        return new BadgeRes(badge);
    }

    @Override
    public BadgeCheckRes checkBadgeOneToOne(EmotionDataReq emotionDataReq) throws NotFoundException {
        BadgeCondition badgeCondition = badgeConditionRepository.findBadgeConditionByUserSeq(emotionDataReq.getUserSeq())
                .orElseThrow(() -> new NotFoundException("Badge condition not found!"));

        List<BadgeRes> obtainedBadges = new ArrayList<>();
        Long obtainedPoints = 0L;

        int finCount = badgeCondition.addOneToOneFinCount();

        // 첫 일대일 미팅 완료, 일대일 미팅 5, 10, 20회 완료
        if(finCount == 1) {
            obtainedBadges.add(getBadgeBySeq(1L));
            obtainedPoints += 100L;
        }
        else if(finCount == 5) {
            obtainedBadges.add(getBadgeBySeq(2L));
            obtainedPoints += 200L;
        }
        else if(finCount == 10) {
            obtainedBadges.add(getBadgeBySeq(3L));
            obtainedPoints += 300L;
        }
        else if(finCount == 20) {
            obtainedBadges.add(getBadgeBySeq(4L));
            obtainedPoints += 400L;
        }

        Double[] emotionDataArr = emotionDataReq.emotionDataArr();

        long idx = -1;
        Double max = 0.0;

        // 가장 많이 유발한 감정의 인덱스 (anger, disgust, fear, happiness, sadness, surprise 순)
        for(int i = 0; i < emotionDataArr.length; ++i) {
            if(emotionDataArr[i] > max) {
                max = emotionDataArr[i];
                idx = i;
            }
        }

        int emotionCount = badgeCondition.addEmotionCount(idx);

        // 감정 5, 10, 15회 유발
        if(emotionCount == 5) {
            obtainedBadges.add(getBadgeBySeq(3*idx+13));
            obtainedPoints += 100L;
        }
        else if(emotionCount == 10) {
            obtainedBadges.add(getBadgeBySeq(3*idx+14));
            obtainedPoints += 200L;
        }
        else if(emotionCount == 15) {
            obtainedBadges.add(getBadgeBySeq(3*idx+15));
            obtainedPoints += 300L;
        }

        // 획득한 뱃지가 있으면 유저에게 지급
        for(BadgeRes badge: obtainedBadges) {
            UserBadge userBadge = UserBadge.builder()
                    .user(userRepository.findById(emotionDataReq.getUserSeq())
                            .orElseThrow(() -> new NotFoundException("Wrong user Seq!")))
                    .badge(badgeRepository.findById(badge.getSeq())
                            .orElseThrow(() -> new NotFoundException("Wrong Badge Seq!")))
                    .build();

            userBadgeRepository.save(userBadge);
        }

        // 유저에게 포인트 지급
        if(obtainedPoints != 0) userService.addPoint(emotionDataReq.getUserSeq(), obtainedPoints);

        return new BadgeCheckRes(obtainedBadges, obtainedPoints);
    }

    @Override
    public BadgeCheckRes checkBadgeManyToMany(Long userSeq) throws NotFoundException {
        BadgeCondition badgeCondition = badgeConditionRepository.findBadgeConditionByUserSeq(userSeq)
                .orElseThrow(() -> new NotFoundException("Badge condition not found!"));

        List<BadgeRes> obtainedBadges = new ArrayList<>();
        Long obtainedPoints = 0L;

        int finCount = badgeCondition.addManyToManyFinCount();

        // 첫 일대일 미팅 완료, 일대일 미팅 5, 10, 20회 완료
        if(finCount == 1) {
            obtainedBadges.add(getBadgeBySeq(5L));
            obtainedPoints += 100L;
        }
        else if(finCount == 5) {
            obtainedBadges.add(getBadgeBySeq(6L));
            obtainedPoints += 200L;
        }
        else if(finCount == 10) {
            obtainedBadges.add(getBadgeBySeq(7L));
            obtainedPoints += 300L;
        }
        else if(finCount == 20) {
            obtainedBadges.add(getBadgeBySeq(8L));
            obtainedPoints += 400L;
        }

        // 획득한 뱃지가 있으면 유저에게 지급
        for(BadgeRes badge: obtainedBadges) {
            UserBadge userBadge = UserBadge.builder()
                    .user(userRepository.findById(userSeq)
                            .orElseThrow(() -> new NotFoundException("Wrong user Seq!")))
                    .badge(badgeRepository.findById(badge.getSeq())
                            .orElseThrow(() -> new NotFoundException("Wrong Badge Seq!")))
                    .build();

            userBadgeRepository.save(userBadge);
        }

        // 유저에게 포인트 지급
        if(obtainedPoints != 0) userService.addPoint(userSeq, obtainedPoints);

        return new BadgeCheckRes(obtainedBadges, obtainedPoints);
    }

    @Override
    public BadgeCheckRes checkBadgeItem(Long userSeq) throws NotFoundException {
        BadgeCondition badgeCondition = badgeConditionRepository.findBadgeConditionByUserSeq(userSeq)
                .orElseThrow(() -> new NotFoundException("Badge condition not found!"));

        List<BadgeRes> obtainedBadges = new ArrayList<>();
        Long obtainedPoints = 0L;

        int itemCount = badgeCondition.addItemCount();

        // 첫 일대일 미팅 완료, 일대일 미팅 5, 10, 20회 완료
        if(itemCount == 1) {
            obtainedBadges.add(getBadgeBySeq(9L));
            obtainedPoints += 100L;
        }
        else if(itemCount == 5) {
            obtainedBadges.add(getBadgeBySeq(10L));
            obtainedPoints += 200L;
        }
        else if(itemCount == 10) {
            obtainedBadges.add(getBadgeBySeq(11L));
            obtainedPoints += 300L;
        }
        else if(itemCount == 20) {
            obtainedBadges.add(getBadgeBySeq(12L));
            obtainedPoints += 400L;
        }

        // 획득한 뱃지가 있으면 유저에게 지급
        for(BadgeRes badge: obtainedBadges) {
            UserBadge userBadge = UserBadge.builder()
                    .user(userRepository.findById(userSeq)
                            .orElseThrow(() -> new NotFoundException("Wrong user Seq!")))
                    .badge(badgeRepository.findById(badge.getSeq())
                            .orElseThrow(() -> new NotFoundException("Wrong Badge Seq!")))
                    .build();

            userBadgeRepository.save(userBadge);
        }

        // 유저에게 포인트 지급
        if(obtainedPoints != 0) userService.addPoint(userSeq, obtainedPoints);

        return new BadgeCheckRes(obtainedBadges, obtainedPoints);
    }
}
