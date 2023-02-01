package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.request.game.BR31CreateReq;
import com.ssafy.a107.api.request.game.BR31Req;
import com.ssafy.a107.api.response.game.BR31Res;
import com.ssafy.a107.api.service.GameService;
import com.ssafy.a107.common.exception.NotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Api("멀티 미팅방 게임 관련 컨트롤러 ---- 세션사용으로 바꿔야함")
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @ApiOperation("새로운 배스킨라빈스 게임 세션을 생성")
    @PostMapping("/br31/start")
    public ResponseEntity<?> startBR31(@RequestBody BR31CreateReq br31CreateReq) throws NotFoundException {
        BR31Res br31Res = gameService.createBRGameSession(br31CreateReq);
        return ResponseEntity.status(HttpStatus.OK).body(br31Res);
    }

    @ApiOperation("배스킨 라빈스 게임 진행")
    @PostMapping("/br31")
    public ResponseEntity<?> setBR31point(@RequestBody BR31Req br31Req) throws NotFoundException {
        BR31Res br31Res = gameService.setBR31point(br31Req);
        return ResponseEntity.status(HttpStatus.OK).body(br31Res);
    }
}
