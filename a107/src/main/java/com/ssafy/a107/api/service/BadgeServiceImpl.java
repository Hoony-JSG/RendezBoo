package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.BadgeCheckReq;
import com.ssafy.a107.api.request.BadgeCreateReq;
import com.ssafy.a107.api.request.BadgeUpdateReq;
import com.ssafy.a107.api.request.UserBadgeReq;
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
public class BadgeServiceImpl implements BadgeService {

    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;
    private final UserBadgeRepository userBadgeRepository;
    private final BadgeConditionRepository badgeConditionRepository;
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

    @Override
    public BadgeCheckRes checkBadge(Long userSeq, BadgeCheckReq badgeCheckReq) throws NotFoundException {
        User user = userRepository.findById(userSeq)
                .orElseThrow(() -> new NotFoundException("Wrong user Seq!"));

        BadgeCondition badgeCondition = badgeConditionRepository.findBadgeConditionByUserSeq(userSeq)
                .orElseThrow(() -> new NotFoundException("Badge condition not found!"));

        List<BadgeRes> obtainedBadges = new ArrayList<>();
        Long obtainedPoints = 0L;

        // 미팅을 끝까지 진행했으면
        if(badgeCheckReq.getFinishedMeeting()) {
            int finCount = badgeCondition.addFinCount();

            // TODO: 뱃지, 획득조건, 포인트 보상 정하기
            if(finCount == 5) {
                //obtainedBadges.add(뱃지seq);
                //obtainedPoints += 포인트 보상;
            }
            else if(finCount == 10) {

            }
            else if(finCount == 20) {

            }

            // 가장 많이 유발한 감정 count 1증가 및 뱃지 획득 조건 확인
            if(badgeCheckReq.getTop1Emotion() != null) {
                String emotion = badgeCheckReq.getTop1Emotion();

                // TODO
            }
        }

        if(badgeCheckReq.getBoughtItem()) {
            int itemCount = badgeCondition.addItemCount();


        }

        // 새로 획득한 뱃지가 있다면 DB에 추가
        for(BadgeRes badge: obtainedBadges) {
            UserBadge userBadge = UserBadge.builder()
                    .user(user)
                    .badge(badgeRepository.findById(badge.getSeq())
                            .orElseThrow(() -> new NotFoundException("Wrong Badge Seq!")))
                    .build();

            userBadgeRepository.save(userBadge);
        }

        return new BadgeCheckRes(obtainedBadges, obtainedPoints);
    }
}
