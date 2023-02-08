package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.game.*;
import com.ssafy.a107.api.response.MultiChatFlag;
import com.ssafy.a107.api.response.game.BR31Res;
import com.ssafy.a107.api.response.game.FastClickRes;
import com.ssafy.a107.api.response.game.GameOfDeathRes;
import com.ssafy.a107.common.exception.BadRequestException;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.db.entity.game.BR31;
import com.ssafy.a107.db.entity.game.FastClick;
import com.ssafy.a107.db.entity.game.GameOfDeath;
import com.ssafy.a107.db.repository.MultiMeetingRoomRepository;
import com.ssafy.a107.db.repository.game.BR31Repository;
import com.ssafy.a107.db.repository.game.FastClickRepository;
import com.ssafy.a107.db.repository.game.GameOfDeathRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService {

    private final BR31Repository br31Repository;
    private final GameOfDeathRepository gameOfDeathRepository;
    private final FastClickRepository fastClickRepository;
    private final MultiMeetingRoomRepository multiMeetingRoomRepository;

    private static final long redisGameExpiration = 1800000;
    private static Random rd = new Random();

     // 새로운 배스킨라빈스게임 세션 생성
    @Override
    @Transactional
    public BR31Res createBRGameSession(BR31CreateReq br31CreateReq) {
        List<Long> userSeqList = multiMeetingRoomRepository
                .findUserSequencesByMultiMeetingRoomSeq(br31CreateReq.getMultiMeetingRoomSeq());

        // 게임 순서 결정
        Collections.shuffle(userSeqList);

        // 첫 번째 순서
        Long firstTurn = userSeqList.get(0);

        BR31 br31 = BR31.builder()
                .multiMeetingRoomSeq(br31CreateReq.getMultiMeetingRoomSeq())
                .users(userSeqList)
                .nowUser(firstTurn)
                .point(0)
                .expiration(redisGameExpiration) // 30분 뒤 캐시 삭제
                .build();

        br31Repository.save(br31);

        log.debug("************ BR31 게임 시작! 채팅방 번호: {} ************", br31CreateReq.getMultiMeetingRoomSeq());
        log.debug("참여자 명단: {}", userSeqList);
        log.debug("첫 번째 순서: User {}", firstTurn);

        return new BR31Res(br31, "배스킨 라빈스 31 게임 시작!", br31.getNowUser(), MultiChatFlag.SYSTEM);
    }

     // 배스킨 라빈스 게임을 진행
    @Override
    @Transactional
    public BR31Res setBR31point(BR31Req br31Req) throws NotFoundException {
        BR31 br31 = br31Repository.findById(br31Req.getMultiMeetingRoomSeq())
                .orElseThrow(() -> new NotFoundException("No game Session!"));

        if (!br31.getNowUser().equals(br31Req.getUserSeq()) || br31Req.getPoint() > 3 || br31Req.getPoint() < 1) {
            throw new NotFoundException("Wrong Request!");
        }

        log.debug("************ BR31 게임 진행, 채팅방 번호: {} ************", br31Req.getMultiMeetingRoomSeq());
        log.debug("숫자: {}, 유저: {}, 선택한 숫자: {}", br31.getPoint(), br31Req.getUserSeq(), br31Req.getPoint());

        br31.addPoint(br31Req.getPoint());

        log.debug("더한 후 숫자: {}", br31.getPoint());

        int nextUserSeqIndex = (br31.getUsers().indexOf(br31Req.getUserSeq()) + 1) % 6;
        Long nextUserSeq = br31.getUsers().get(nextUserSeqIndex);
        br31.setNextUser(nextUserSeq);

        if (br31.getPoint() == 30) {
            //게임이 끝난 경우
            br31Repository.delete(br31);
            log.debug("게임 종료! 유저 {} 패배", br31.getNowUser());
            String msg = "30을 말하셔서 다음 분이 패배하셨습니다.";
            return new BR31Res(br31, msg, br31.getNowUser(), MultiChatFlag.FIN);
        } else if (br31.getPoint() >= 31) {
            // 게임이 끝난 경우 2
            br31Repository.delete(br31);
            log.debug("게임 종료! 유저 {} 패배", br31Req.getUserSeq());
            String msg = "31을 말하셔서 패배하셨습니다.";
            return new BR31Res(br31, msg, br31Req.getUserSeq(), MultiChatFlag.FIN);
        } else {
            // 게임이 지속될 경우
            br31Repository.save(br31);
            log.debug("다음 유저: User {}", nextUserSeq);
            String msg = "다음 분의 차례입니다.";
            return new BR31Res(br31, msg, br31.getNowUser(), MultiChatFlag.GAME);
        }
    }

    @Override
    @Transactional
    public GameOfDeathRes createGameOfDeathSession(GameOfDeathCreateReq createReq) throws BadRequestException {
        log.debug("************ GameOfDeath 게임 시작! 채팅방 번호: {} ************", createReq.getMultiMeetingRoomSeq());

        if(createReq.getTargets() == null) {
            throw new BadRequestException("No targets argument!");
        }

        List<Long> userSeqList = multiMeetingRoomRepository
                .findUserSequencesByMultiMeetingRoomSeq(createReq.getMultiMeetingRoomSeq());

        // 시간초과로 targetSeq를 선택하지 않았을 경우 -> 랜덤으로 선택
        for(int i = 0; i < userSeqList.size(); ++i) {
            long curUser = userSeqList.get(i);

            if(createReq.getTargets().get(curUser) == null) {
                int randIdx = -1;

                while(true) {
                    randIdx = rd.nextInt(userSeqList.size());

                    if(userSeqList.get(randIdx) != curUser) break;
                }

                log.debug("랜덤 선택");
                log.debug("User {} --> User {}", curUser, userSeqList.get(randIdx));
                createReq.getTargets().put(curUser, userSeqList.get(randIdx));
            }
        }

        GameOfDeath gameOfDeath = GameOfDeath.builder()
                .targets(createReq.getTargets())
                .startUserSeq(createReq.getStartUserSeq())
                .multiMeetingRoomSeq(createReq.getMultiMeetingRoomSeq())
                .build();

        gameOfDeathRepository.save(gameOfDeath);

        log.debug("참여자 명단: {}", userSeqList);
        log.debug("지목 현황: {}", createReq.getTargets());

        return new GameOfDeathRes(gameOfDeath, null, 0L, "GameOfDeath가 시작되었습니다.", MultiChatFlag.SYSTEM);
    }

    @Override
    @Transactional
    public GameOfDeathRes runGameOfDeathSession(GameOfDeathReq gameOfDeathReq) throws NotFoundException {
        log.debug("************ GameOfDeath 게임 진행! 채팅방 번호: {} ************", gameOfDeathReq.getMultiMeetingRoomSeq());

        if (gameOfDeathReq.getTurn() < 3 || gameOfDeathReq.getTurn() > 20) {
            throw new NotFoundException("Wrong Request");
        }

        log.debug("선택한 Turn 수: {}", gameOfDeathReq.getTurn());

        GameOfDeath gameOfDeath = gameOfDeathRepository.findById(gameOfDeathReq.getMultiMeetingRoomSeq())
                .orElseThrow(() -> new NotFoundException("Wrong meeting room seq!"));

        Map<Long, Long> targets = gameOfDeath.getTargets();
        log.debug("지목 현황: {}", targets);

        // 게임 진행
        List<Long> resultList = new ArrayList<>();
        int turn = gameOfDeathReq.getTurn();
        long cur = gameOfDeath.getStartUserSeq();
        resultList.add(cur);

        while(turn-- > 0) {
            cur = targets.get(cur);
            resultList.add(cur);
        }

        log.debug("게임 기록: {}", resultList);
        log.debug("User {} 패배!", cur);
        return new GameOfDeathRes(gameOfDeath, resultList, cur, "GameOfDeath가 종료되었습니다.",MultiChatFlag.FIN);
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
