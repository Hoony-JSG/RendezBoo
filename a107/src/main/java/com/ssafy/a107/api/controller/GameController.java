package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.request.game.BR31CreateReq;
import com.ssafy.a107.api.request.game.BR31Req;
import com.ssafy.a107.api.response.game.BR31Res;
import com.ssafy.a107.api.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @PostMapping("/br31")
    public ResponseEntity<?> startBR31(@RequestBody BR31CreateReq br31CreateReq) {
        String sessionId = gameService.createBRGameSession(br31CreateReq);
        return ResponseEntity.status(HttpStatus.OK).body(sessionId);
    }

    @PutMapping("/br31")
    public ResponseEntity<?> setBR31point(@RequestBody BR31Req br31Req) {
        BR31Res br31Res = gameService.setBR31point(br31Req);
        return ResponseEntity.status(HttpStatus.OK).body(br31Res);
    }
}
