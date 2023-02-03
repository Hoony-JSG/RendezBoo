package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.game.BR31CreateReq;
import com.ssafy.a107.api.request.game.BR31Req;
import com.ssafy.a107.api.request.game.GameOfDeathCreateReq;
import com.ssafy.a107.api.request.game.GameOfDeathReq;
import com.ssafy.a107.api.response.game.BR31Res;
import com.ssafy.a107.api.response.game.GameOfDeathRes;
import com.ssafy.a107.common.exception.NotFoundException;

public interface GameService {
    BR31Res createBRGameSession(BR31CreateReq br31CreateReq) throws NotFoundException;

    BR31Res setBR31point(BR31Req br31Req) throws NotFoundException;

    GameOfDeathRes createGameOfDeathSession(GameOfDeathCreateReq gameOfDeathCreateReq) throws NotFoundException;

    GameOfDeathRes runGameOfDeathSession(GameOfDeathReq gameOfDeathReq) throws NotFoundException;
}
