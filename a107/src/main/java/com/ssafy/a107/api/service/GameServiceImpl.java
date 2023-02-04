package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.game.*;
import com.ssafy.a107.api.response.MultiChatFlag;
import com.ssafy.a107.api.response.game.BR31Res;
import com.ssafy.a107.api.response.game.FastClickRes;
import com.ssafy.a107.api.response.game.GameOfDeathRes;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.db.entity.game.BR31;
import com.ssafy.a107.db.entity.game.FastClick;
import com.ssafy.a107.db.entity.game.GameOfDeath;
import com.ssafy.a107.db.repository.MultiMeetingRoomRepository;
import com.ssafy.a107.db.repository.game.BR31Repository;
import com.ssafy.a107.db.repository.game.FastClickRepository;
import com.ssafy.a107.db.repository.game.GameOfDeathRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final BR31Repository br31Repository;

    private final GameOfDeathRepository gameOfDeathRepository;

    private final FastClickRepository fastClickRepository;

    private final MultiMeetingRoomRepository multiMeetingRoomRepository;

    /**
     * 새로운 배스킨라빈스게임 세션 생성
     *
     * @param br31CreateReq
     * @return
     * @throws NotFoundException
     */
    @Override
    @Transactional
    public BR31Res createBRGameSession(BR31CreateReq br31CreateReq) throws NotFoundException {
        List<Long> userSeqList = multiMeetingRoomRepository
                .findUserSequencesByMultiMeetingRoomSeq(br31CreateReq.getMultiMeetingRoomSeq());
        BR31 br31 = BR31.builder().sessionId(System.currentTimeMillis())
                .multiMeetingRoomSeq(br31CreateReq.getMultiMeetingRoomSeq())
                .users(userSeqList)
                .nowUser(br31CreateReq.getUserSeq())
                .point(0)
                .build();
        br31Repository.save(br31);

        return new BR31Res(br31, "배스킨 라빈스 31 게임 시작!!!", br31.getNowUser(), MultiChatFlag.SYSTEM);
    }

    /**
     * 배스킨 라빈스 게임을 진행
     *
     * @param br31Req
     * @return
     * @throws NotFoundException
     */
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
            //게임이 끝난 경우
            String msg = "30을 말하셔서 다음 분이 패배하셨습니다.";
            return new BR31Res(br31, msg, br31.getNowUser(), MultiChatFlag.SYSTEM);
        } else if (br31.getPoint() >= 31) {
            // 게임이 끝난 경우 2
            String msg = "31을 말하셔서 패배하셨습니다.";
            return new BR31Res(br31, msg, br31Req.getUserSeq(), MultiChatFlag.SYSTEM);
        } else {
            // 게임이 지속될 경우
            String msg = "다음 분의 차례입니다.";
            return new BR31Res(br31, msg, br31.getNowUser(), MultiChatFlag.GAME);
        }

    }

    @Override
    @Transactional
    public GameOfDeathRes createGameOfDeathSession(GameOfDeathCreateReq createReq) throws NotFoundException {
        if (createReq.getCount() < 3 || createReq.getCount() > 20) {
            throw new NotFoundException("Wrong Request");
        }

        GameOfDeath gameOfDeath = GameOfDeath.builder()
                .gameId(System.currentTimeMillis())
                .count(createReq.getCount())
                .startUserSeq(createReq.getStartUserSeq())
                .multiMeetingRoomSeq(createReq.getMultiMeetingRoomSeq())
                .targets(Map.of())
                .build();
        gameOfDeathRepository.save(gameOfDeath);
        GameOfDeathRes gameOfDeathRes = new GameOfDeathRes(gameOfDeath, List.of(), 0L, MultiChatFlag.SYSTEM);
        return gameOfDeathRes;
    }

    @Override
    @Transactional
    public GameOfDeathRes runGameOfDeathSession(GameOfDeathReq gameOfDeathReq) throws NotFoundException {
        GameOfDeath gameOfDeath = gameOfDeathRepository.findById(gameOfDeathReq.getGameId())
                .orElseThrow(() -> new NotFoundException("Wrong GameId"));
        Map<Long, Long> targets = gameOfDeath.getTargets();
        targets.put(gameOfDeathReq.getUserSeq(), gameOfDeathReq.getTargetSeq());
        gameOfDeath.updateTargets(targets);
        gameOfDeathRepository.save(gameOfDeath);

        // 게임이 종료된 경우
        if (targets.size() == 6) {
            List<Long> countingList = List.of();
            Long loseUserSeq = targets.get(gameOfDeath.getStartUserSeq());
            countingList.add(loseUserSeq);

            for (int i = 1; i < gameOfDeath.getCount(); i++) {
                loseUserSeq = targets.get(loseUserSeq);
                countingList.add(loseUserSeq);
            }

            return new GameOfDeathRes(gameOfDeath, countingList, loseUserSeq, MultiChatFlag.SYSTEM);
        }

        return new GameOfDeathRes(gameOfDeath, List.of(), 0L, MultiChatFlag.GAME);
    }

    @Override
    public FastClickRes createFastClickSession(FastClickCreateReq fastClickCreateReq) throws NotFoundException {
        List<Long> userSeqList = multiMeetingRoomRepository.findUserSequencesByMultiMeetingRoomSeq(fastClickCreateReq.getMultiMeetingRoomSeq());
        FastClick fastClick = FastClick.builder().sessionId(System.currentTimeMillis())
                .multiMeetingRoomSeq(fastClickCreateReq.getMultiMeetingRoomSeq())
                .users(userSeqList)
                .build();
        fastClickRepository.save(fastClick);

        return new FastClickRes();
    }

    @Override
    public FastClickRes runFastClickSession(FastClickReq fastClickReq) throws NotFoundException {
        //get all scores from 6 users


        return null;
    }
}
