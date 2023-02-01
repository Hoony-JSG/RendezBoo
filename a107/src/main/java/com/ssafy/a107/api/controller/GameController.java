package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.request.game.BR31CreateReq;
import com.ssafy.a107.api.request.game.BR31Req;
import com.ssafy.a107.api.response.game.BR31Res;
import com.ssafy.a107.api.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @PostMapping("/br31/start")
    public ResponseEntity<?> startBR31(@RequestBody BR31CreateReq br31CreateReq) {
        BR31Res br31Res = gameService.createBRGameSession(br31CreateReq);
        return ResponseEntity.status(HttpStatus.OK).body(br31Res);
    }

    @PostMapping("/br31")
    public ResponseEntity<?> setBR31point(@RequestBody BR31Req br31Req) {
        BR31Res br31Res = gameService.setBR31point(br31Req);
        return ResponseEntity.status(HttpStatus.OK).body(br31Res);
    }
}
