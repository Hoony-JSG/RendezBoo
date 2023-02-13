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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EmotionDataServiceImpl implements EmotionDataService{

    private final EmotionDataRepository emotionDataRepository;
    private final UserRepository userRepository;
    private final OneToOneMeetingRoomRepository onetoOneMeetingRoomRepository;
    @Transactional
    @Override
    public Long addExpressionData(EmotionDataReq req) throws NotFoundException{
        User user = userRepository.findById(req.getUserSeq())
                .orElseThrow(()->new NotFoundException("Invalid user sequence!"));
        OnetoOneMeetingRoom room = onetoOneMeetingRoomRepository.findById(req.getMeetingRoomSeq())
                .orElseThrow(()->new NotFoundException("Invalid Meeting Room sequence!"));
        EmotionData e = emotionDataRepository.save(EmotionData.builder()
                .user(user)
                .meetingRoom(room)
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
    public EmotionDataRes getAvgExpressionDataByUserSeq(Long userSeq) throws NotFoundException{
        if(!userRepository.existsById(userSeq)) throw new NotFoundException("Wrong User Seq!");

        List<EmotionData> elist = emotionDataRepository.findTop10ByUserSeq(userSeq);
        double anger = 0.0, contempt = 0.0, disgust = 0.0,
                fear = 0.0, happiness = 0.0, neutral = 0.0,
                sadness = 0.0, surprise = 0.0;
        int len = elist.size();
        for(EmotionData e: elist){
            anger += e.getAnger();
            contempt += e.getContempt();
            disgust += e.getDisgust();
            fear += e.getDisgust();
            happiness += e.getHappiness();
            neutral += e.getNeutral();
            sadness += e.getSadness();
            surprise += e.getSurprise();
        }
        return len == 0? new EmotionDataRes() : new EmotionDataRes(
         anger/len, contempt/len, disgust/len, fear/len,
                happiness/len, neutral/len, sadness/len, surprise/len
        );
    }
}
