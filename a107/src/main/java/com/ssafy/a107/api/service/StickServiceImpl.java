package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.StickCreateReq;
import com.ssafy.a107.api.request.StickReq;
import com.ssafy.a107.api.response.MultiChatFlag;
import com.ssafy.a107.api.response.StickRes;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.db.entity.Stick;
import com.ssafy.a107.db.repository.MultiMeetingRoomRepository;
import com.ssafy.a107.db.repository.StickRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class StickServiceImpl implements StickService {

    private final MultiMeetingRoomRepository multiMeetingRoomRepository;
    private final StickRepository stickRepository;

    private static final long stickOfLoveExpiration = 3600000;

    @Override
    public StickRes createStickOfLove(StickCreateReq stickCreateReq) {
        log.debug("************ 사랑의 작대기 시작! 채팅방 번호: {} ************", stickCreateReq.getMultiMeetingRoomSeq());

        List<Long> userSeqList = multiMeetingRoomRepository
                .findUserSequencesByMultiMeetingRoomSeq(stickCreateReq.getMultiMeetingRoomSeq());

        Stick stick = Stick.builder()
                .multiMeetingRoomSeq(stickCreateReq.getMultiMeetingRoomSeq())
                .expiration(stickOfLoveExpiration) // 60분 뒤 캐시 삭제
                .build();

        stickRepository.save(stick);

        log.debug("참여자 명단: {}", userSeqList);

        return new StickRes(stick, null, "사랑의 작대기가 시작되었습니다.", MultiChatFlag.START);
    }

    @Override
    public StickRes runStickOfLove(StickReq stickReq) throws NotFoundException {
        log.debug("************ 사랑의 작대기 진행, 채팅방 번호: {} ************", stickReq.getMultiMeetingRoomSeq());
        Long userSeq = stickReq.getUserSeq();
        Long targetSeq = stickReq.getTargetSeq();

        Stick stick = stickRepository.findById(stickReq.getMultiMeetingRoomSeq())
                .orElseThrow(() -> new NotFoundException("Wrong meeting room seq!"));

        List<Long> userSeqList = multiMeetingRoomRepository
                .findUserSequencesByMultiMeetingRoomSeq(stickReq.getMultiMeetingRoomSeq());
        log.debug("참여 유저 목록: {}", userSeqList);

        Map<Long, Long> targets = stick.getTargets();
        if(targets == null) targets = new HashMap<>();

        targets.put(userSeq, targetSeq);
        stick.setTargets(targets);

        if(targetSeq == 0) {
            log.debug("유저 {}, 아무도 선택하지 않음", userSeq);
        }
        else {
            log.debug("유저 {} --> 유저 {}", userSeq, targetSeq);
        }

        log.debug("지목 현황: {}", targets);

        // 지목 현황을 모두 받았으면 게임 진행
        if(targets.size() == userSeqList.size()) {
            List<long[]> matches = new ArrayList<>();

            for(Map.Entry<Long, Long> entry: targets.entrySet()) {
                Long temp = targets.get(entry.getValue());

                // 상대방을 선택하지 않은 경우
                if(temp == null) continue;

                if(entry.getKey() == temp && entry.getKey() < entry.getValue()) {
                    long[] match = {entry.getKey(), entry.getValue()};
                    matches.add(match);
                }
            }

            log.debug("사랑의 작대기 종료");
            log.debug("매칭 성공: {}", matches);
            
            return new StickRes(stick, matches, "사랑의 작대기가 종료되었습니다.", MultiChatFlag.FIN);
        }
        else {
            stickRepository.save(stick);
            
            log.debug("다음 유저 진행");

            return new StickRes(stick, null, "사랑의 작대기 진행", MultiChatFlag.GAME);
        }
    }
}
