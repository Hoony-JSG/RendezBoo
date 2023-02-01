package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.game.BR31CreateReq;
import com.ssafy.a107.api.request.game.BR31Req;
import com.ssafy.a107.api.response.game.BR31Res;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.db.entity.User;
import com.ssafy.a107.db.entity.game.BR31;
import com.ssafy.a107.db.repository.MultiMeetingRoomRepository;
import com.ssafy.a107.db.repository.game.BR31Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final BR31Repository br31Repository;

    private final MultiMeetingRoomRepository multiMeetingRoomRepository;

    @Override
    @Transactional
    public BR31Res createBRGameSession(BR31CreateReq br31CreateReq) {
        List<Long> userSeqList = null;
        BR31 br31 = BR31.builder().sessionId(System.currentTimeMillis())
                .multiMeetingRoomSeq(br31CreateReq.getMultiMeetingRoomSeq())
                .users(userSeqList)
                .nowUser(br31CreateReq.getUserSeq())
                .point(0)
                .build();
        br31Repository.save(br31);

        return new BR31Res(br31, "배스킨 라빈스 31 게임 시작!!!", br31.getNowUser());
    }

    @Override
    @Transactional
    public BR31Res setBR31point(BR31Req br31Req) throws NotFoundException {
        BR31 br31 = br31Repository.findById(br31Req.getBr31SessionId())
                .orElseThrow(() -> new NotFoundException("No game Session!"));

        if (!br31.getNowUser().equals(br31Req.getUserSeq()) || br31Req.getPoint() > 3 || br31Req.getPoint() < 1) {
            throw new NotFoundException("Wrong Request!");
        }
        br31.addPoint(br31Req.getPoint());
        int nextUserSeqIndex = (br31.getUsers().indexOf(br31Req.getUserSeq()) + 1) % 6;
        Long nextUserSeq = br31.getUsers().get(nextUserSeqIndex);
        br31.setNextUser(nextUserSeq);
        br31Repository.save(br31);
        if (br31.getPoint() == 30) {
            String msg = "30을 말하셔서 다음 분이 패배하셨습니다.";
            return new BR31Res(br31, msg, br31.getNowUser());
        } else if (br31.getPoint() >= 31) {
            String msg = "31을 말하셔서 패배하셨습니다.";
            return new BR31Res(br31, msg, br31Req.getUserSeq());
        } else {
            String msg = "다음 분의 차례입니다.";
            return new BR31Res(br31, msg, br31.getNowUser());
        }

    }
}
