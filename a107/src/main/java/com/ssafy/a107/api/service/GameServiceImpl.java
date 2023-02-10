package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.game.*;
import com.ssafy.a107.api.response.MultiChatFlag;
import com.ssafy.a107.api.response.game.BR31Res;
import com.ssafy.a107.api.response.game.FastClickRes;
import com.ssafy.a107.api.response.game.GameOfDeathRes;
import com.ssafy.a107.common.exception.ConflictException;
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
                .order(userSeqList)
                .nowUser(firstTurn)
                .point(0)
                .expiration(redisGameExpiration) // 30분 뒤 캐시 삭제
                .build();

        br31Repository.save(br31);

        log.debug("************ BR31 게임 시작! 채팅방 번호: {} ************", br31CreateReq.getMultiMeetingRoomSeq());
        log.debug("참여자 명단: {}", userSeqList);
        log.debug("첫 번째 순서: User {}", firstTurn);

        return new BR31Res(br31, "배스킨 라빈스 31 게임 시작!", br31.getNowUser(), MultiChatFlag.START);
    }

     // 배스킨 라빈스 게임을 진행
    @Override
    @Transactional
    public BR31Res setBR31point(BR31Req br31Req) throws NotFoundException, ConflictException {
        BR31 br31 = br31Repository.findById(br31Req.getMultiMeetingRoomSeq())
                .orElseThrow(() -> new NotFoundException("No game Session!"));

        if (!br31.getNowUser().equals(br31Req.getUserSeq()) || br31Req.getPoint() > 3 || br31Req.getPoint() < 1) {
            throw new NotFoundException("Wrong Request!");
        }

        // 나간 유저 체크
        List<Long> newOrder = new ArrayList<>();
        List<Long> curOrder = br31.getOrder();
        boolean isCurUserExist = true;
        List<Long> userSeqList = multiMeetingRoomRepository
                .findUserSequencesByMultiMeetingRoomSeq(br31Req.getMultiMeetingRoomSeq());

        // 현재 채팅방에 있는 유저 set
        Set<Long> userSeqSet = new HashSet<>();
        for(Long userSeq: userSeqList) {
            userSeqSet.add(userSeq);
        }

        // 채팅방 유저 수와 순서 리스트의 사이즈가 다르면 -> 누군가 나갔음
        if(userSeqList.size() != curOrder.size()) {
            log.debug("유저 중도 퇴장(유저 수: {} -> {})", curOrder.size(), userSeqList.size());

            for(Long userSeq: br31.getOrder()) {
                newOrder.add(userSeq);
            }

            for(int i = newOrder.size()-1; i >= 0; --i) {
                Long curSeq = newOrder.get(i);

                if(!userSeqSet.contains(curSeq)) {
                    // 현재 차례 유저가 숫자를 고르고 나간 경우
                    if(curSeq == br31Req.getUserSeq()) {
                        isCurUserExist = false;
                    }

                    newOrder.remove(i);
                }
            }
        }

        log.debug("************ BR31 게임 진행, 채팅방 번호: {} ************", br31Req.getMultiMeetingRoomSeq());
        log.debug("숫자: {}, 유저: {}, 선택한 숫자: {}", br31.getPoint(), br31Req.getUserSeq(), br31Req.getPoint());

        Long nextUserSeq = 0L;

        // 누군가 나갔으면
        if(userSeqList.size() != curOrder.size()) {
            if(newOrder.size() == 1) {
                log.debug("유저가 한 명 남아서 게임 종료");
                throw new ConflictException("Cannot continue game: only one user exists.");
            }

            int nextIdx = curOrder.indexOf(br31Req.getUserSeq()) + 1;

            while(!userSeqSet.contains(curOrder.get(nextIdx))) {
                nextIdx = (nextIdx + 1) % curOrder.size();
            }

            br31.setOrder(newOrder);
            nextUserSeq = curOrder.get(nextIdx);
            br31.setNextUser(nextUserSeq);

            // 나간 유저가 현재 유저가 아니면
            if(isCurUserExist) {
                log.debug("현재 유저가 아닌 다른 유저가 퇴장");

                br31.addPoint(br31Req.getPoint());
                log.debug("더한 후 숫자: {}", br31.getPoint());
            }
            // 현재 유저가 숫자를 고르고 나갔으면
            else {
                log.debug("현재 유저가 숫자를 고르고 퇴장");
                log.debug("다음 차례로 진행");
            }
        }
        // 아무도 안 나갔으면
        else {
            int curIdx = curOrder.indexOf(br31Req.getUserSeq());
            int nextIdx = (curIdx + 1) % curOrder.size();
            nextUserSeq = curOrder.get(nextIdx);
            br31.setNextUser(nextUserSeq);

            br31.addPoint(br31Req.getPoint());
            log.debug("더한 후 숫자: {}", br31.getPoint());
        }

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
    public GameOfDeathRes createGameOfDeathSession(GameOfDeathCreateReq createReq) {
        log.debug("************ GameOfDeath 게임 시작! 채팅방 번호: {} ************", createReq.getMultiMeetingRoomSeq());

        List<Long> userSeqList = multiMeetingRoomRepository
                .findUserSequencesByMultiMeetingRoomSeq(createReq.getMultiMeetingRoomSeq());

        GameOfDeath gameOfDeath = GameOfDeath.builder()
                .startUserSeq(createReq.getStartUserSeq())
                .multiMeetingRoomSeq(createReq.getMultiMeetingRoomSeq())
                .expiration(redisGameExpiration) // 30분 뒤 캐시 삭제
                .count(0)
                .build();

        gameOfDeathRepository.save(gameOfDeath);

        log.debug("참여자 명단: {}", userSeqList);

        return new GameOfDeathRes(gameOfDeath, null, null, "GameOfDeath가 시작되었습니다.", MultiChatFlag.START);
    }

    @Override
    @Transactional
    public GameOfDeathRes runGameOfDeathSession(GameOfDeathReq gameOfDeathReq) throws NotFoundException {
        log.debug("************ GameOfDeath 지목 정보, 채팅방 번호: {} ************", gameOfDeathReq.getMultiMeetingRoomSeq());
        Long userSeq = gameOfDeathReq.getUserSeq();
        Long targetSeq = gameOfDeathReq.getTargetSeq();

        GameOfDeath gameOfDeath = gameOfDeathRepository.findById(gameOfDeathReq.getMultiMeetingRoomSeq())
                .orElseThrow(() -> new NotFoundException("Wrong meeting room seq!"));

        // 시작하는 유저이면 turn 수 저장
        if(userSeq == gameOfDeath.getStartUserSeq()) {
            if (gameOfDeathReq.getTurn() == null || gameOfDeathReq.getTurn() < 3 || gameOfDeathReq.getTurn() > 20) {
                throw new NotFoundException("Wrong Request");
            }

            gameOfDeath.setTurn(gameOfDeathReq.getTurn());
            log.debug("선택한 Turn 수: {}", gameOfDeathReq.getTurn());
        }

        Map<Long, Long> targets = gameOfDeath.getTargets();
        if(targets == null) targets = new HashMap<>();

        // 시간초과로 targetSeq를 선택하지 않았을 경우 -> 랜덤으로 선택
        if(targetSeq == null) {
            List<Long> userSeqList = multiMeetingRoomRepository
                    .findUserSequencesByMultiMeetingRoomSeq(gameOfDeathReq.getMultiMeetingRoomSeq());
            int randIdx = -1;

            while (true) {
                randIdx = rd.nextInt(userSeqList.size());
                targetSeq = userSeqList.get(randIdx);

                if (targetSeq != userSeq) break;
            }

            log.debug("랜덤 선택");
        }

        log.debug("User {} --> User {}", userSeq, targetSeq);
        targets.put(userSeq, targetSeq);

        log.debug("지목 현황: {}", targets);
        gameOfDeath.addCount();

        // 6명의 지목 현황을 모두 받았으면 게임 진행
        if(gameOfDeath.getCount() == 6) {
            List<Long> resultList = new ArrayList<>();
            int turn = gameOfDeath.getTurn();
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
        else {
            gameOfDeathRepository.save(gameOfDeath);
            log.debug("count: {}", gameOfDeath.getCount());

            return new GameOfDeathRes(gameOfDeath, null, null, "GameOfDeath 진행", MultiChatFlag.GAME);
        }
    }

    @Override
    @Transactional
    public FastClickRes createFastClickSession(FastClickCreateReq fastClickCreateReq) {
        log.debug("************ FastClick 게임 시작! 채팅방 번호: {} ************", fastClickCreateReq.getMultiMeetingRoomSeq());

        List<Long> userSeqList = multiMeetingRoomRepository
                .findUserSequencesByMultiMeetingRoomSeq(fastClickCreateReq.getMultiMeetingRoomSeq());

        log.debug("참여자 명단: {}", userSeqList);

        FastClick fastClick = FastClick.builder()
                .multiMeetingRoomSeq(fastClickCreateReq.getMultiMeetingRoomSeq())
                .users(userSeqList)
                .expiration(redisGameExpiration) // 30분 뒤 캐시 삭제
                .build();

        fastClickRepository.save(fastClick);

        return FastClickRes.builder()
                .multiMeetingRoomSeq(fastClick.getMultiMeetingRoomSeq())
                .message("FastClick이 시작되었습니다.")
                .flag(MultiChatFlag.START)
                .build();
    }

    @Override
    public FastClickRes runFastClickSession(FastClickReq fastClickReq) throws NotFoundException {
        log.debug("************ FastClick 게임 결과 수합, 채팅방 번호: {} ************", fastClickReq.getMultiMeetingRoomSeq());
        log.debug("User {}, 클릭 횟수: {}", fastClickReq.getUserSeq(), fastClickReq.getScore());

        FastClick fastClick = fastClickRepository.findById(fastClickReq.getMultiMeetingRoomSeq())
                .orElseThrow(() -> new NotFoundException("No game session!"));

        Map<Long, Integer> scores = fastClick.getScores();
        if(scores == null) scores = new HashMap<>();

        scores.put(fastClickReq.getUserSeq(), fastClickReq.getScore());
        fastClick.addCount();

        log.debug("점수 현황: {}", scores);
        log.debug("count: {}", fastClick.getCount());

        fastClickRepository.save(fastClick);

        // 게임 종료
        if(fastClick.getCount() == 6) {
            List<Long> userSeqList = multiMeetingRoomRepository
                    .findUserSequencesByMultiMeetingRoomSeq(fastClickReq.getMultiMeetingRoomSeq());

            int min = Integer.MAX_VALUE;
            List<Long> minUsers = new ArrayList<>();

            for(long userSeq: userSeqList) {
                int score = fastClick.getScores().get(userSeq);

                if(score < min) {
                    min = score;
                    minUsers.clear();
                    minUsers.add(userSeq);
                }
                else if (score == min) {
                    minUsers.add(userSeq);
                }
            }

            // 최저 점수가 여러명이면 랜덤으로 패배 유저 선정
            if(minUsers.size() > 1) {
                log.debug("동점자 목록: {}", minUsers);
                Collections.shuffle(minUsers);
                Long loseUserSeq = minUsers.get(0);
                log.debug("패배 유저 랜덤 선택: User {}", loseUserSeq);

                return new FastClickRes(fastClick, loseUserSeq, "게임 종료: 동점자 중 패배 유저를 랜덤으로 선택하였습니다.", MultiChatFlag.FIN);
            }
            // 한명이면
            else {
                log.debug("패배 유저: User {}", minUsers.get(0));

                return new FastClickRes(fastClick, minUsers.get(0), "게임 종료!", MultiChatFlag.FIN);
            }
        }
        else {
            return new FastClickRes(fastClick, null, "게임 진행", MultiChatFlag.GAME);
        }
    }
}
