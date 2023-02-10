package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.request.game.FastClickCreateReq;
import com.ssafy.a107.api.request.game.FastClickReq;
import com.ssafy.a107.api.request.game.BR31CreateReq;
import com.ssafy.a107.api.request.game.BR31Req;
import com.ssafy.a107.api.request.game.GameOfDeathCreateReq;
import com.ssafy.a107.api.request.game.GameOfDeathReq;
import com.ssafy.a107.api.response.game.FastClickRes;
import com.ssafy.a107.api.response.game.BR31Res;
import com.ssafy.a107.api.response.game.GameOfDeathRes;
import com.ssafy.a107.api.service.GameService;
import com.ssafy.a107.common.exception.BadRequestException;
import com.ssafy.a107.common.exception.ConflictException;
import com.ssafy.a107.common.exception.NotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Api(value = "멀티 미팅방 게임 관련 컨트롤러 ---- 세션사용으로 바꿔야함")
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @ApiOperation("새로운 배스킨라빈스 게임 세션을 생성")
    @MessageMapping("/br31/start")
    public void startBR31(@RequestBody BR31CreateReq br31CreateReq) {
        BR31Res br31Res = gameService.createBRGameSession(br31CreateReq);
        simpMessageSendingOperations.convertAndSend("/sub/multi/" + br31Res.getMultiMeetingRoomSeq(), br31Res);
    }

    @ApiOperation("배스킨 라빈스 게임 진행")
    @MessageMapping("/br31")
    public void setBR31point(@RequestBody BR31Req br31Req) throws NotFoundException, ConflictException {
        BR31Res br31Res = gameService.setBR31point(br31Req);
        simpMessageSendingOperations.convertAndSend("/sub/multi/" + br31Res.getMultiMeetingRoomSeq(), br31Res);
    }

    @ApiOperation("새로운 더 게임 오브 데스 세션을 생성")
    @MessageMapping("/gameofdeath/start")
    public void startGameOfDeath(@RequestBody GameOfDeathCreateReq gameOfDeathCreateReq) throws BadRequestException {
        GameOfDeathRes gameOfDeathRes = gameService.createGameOfDeathSession(gameOfDeathCreateReq);
        simpMessageSendingOperations.convertAndSend("/sub/multi/" + gameOfDeathRes.getMultiMeetingRoomSeq(), gameOfDeathRes);
    }

    @ApiOperation("더 게임 오브 데스 진행")
    @MessageMapping("/gameofdeath")
    public void runGameOfDeath(@RequestBody GameOfDeathReq gameOfDeathReq) throws NotFoundException, ConflictException {
        GameOfDeathRes gameOfDeathRes = gameService.runGameOfDeathSession(gameOfDeathReq);
        simpMessageSendingOperations.convertAndSend("/sub/multi/" + gameOfDeathRes.getMultiMeetingRoomSeq(), gameOfDeathRes);
    }

    @ApiOperation("새로운 누가누가 빨리 클릭하나 세션을 생성")
    @MessageMapping("/fastclick/start")
    public void startFastClick(@RequestBody FastClickCreateReq fastClickCreateReq) {
        FastClickRes fastClickRes = gameService.createFastClickSession(fastClickCreateReq);
        simpMessageSendingOperations.convertAndSend("/sub/multi/" + fastClickRes.getMultiMeetingRoomSeq(), fastClickRes);
    }

    @ApiOperation("누가누가 빨리 클릭하나 게임 진행")
    @MessageMapping("/fastclick")
    public void runFastClick(@RequestBody FastClickReq fastClickReq) throws NotFoundException {
        FastClickRes fastClickRes = gameService.runFastClickSession(fastClickReq);
        simpMessageSendingOperations.convertAndSend("/sub/multi/" + fastClickRes.getMultiMeetingRoomSeq(), fastClickRes);
    }
}
