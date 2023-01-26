package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.EmotionDataReq;
import com.ssafy.a107.api.response.EmotionDataRes;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.db.entity.EmotionData;
import com.ssafy.a107.db.entity.OnetoOneMeetingRoom;
import com.ssafy.a107.db.entity.User;
import com.ssafy.a107.db.repository.EmotionDataRepository;
import com.ssafy.a107.db.repository.OneToOneMeetingRoomRepository;
import com.ssafy.a107.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EmotionDataServiceImpl implements EmotionDataService {

    private final EmotionDataRepository emotionDataRepository;
    private final UserRepository userRepository;
    private final OneToOneMeetingRoomRepository onetoOneMeetingRoomRepository;
    @Override
    public Long addExpressionData(EmotionDataReq req) throws NotFoundException {
        //OnetoOneMeetingRoom room = onetoOneMeetingRoomRepository.findById(req.getUser_seq()).get();
        EmotionData e = emotionDataRepository.save(EmotionData.builder()
                .user(userRepository.findById(req.getUser_seq())
                        .orElseThrow(() -> new NotFoundException("Wrong User Seq!")))
                //.meetingRoom(room)
                .anger(req.getAnger())
                .contempt(req.getContempt())
                .disgust(req.getDisgust())
                .fear(req.getFear())
                .happiness(req.getHappiness())
                .neutral(req.getNeutral())
                .sadness(req.getSadness())
                .surprise(req.getSurprise()).build());
        return e.getSeq();
    }

    @Override
    public EmotionDataRes getAvgExpressionDataByUserSeq(Long userSeq) {
        Optional<User> user = userRepository.findById(userSeq);
        //수정할 부분
        EmotionData e = EmotionData.builder()
                .build();

        return null;
    }
}
