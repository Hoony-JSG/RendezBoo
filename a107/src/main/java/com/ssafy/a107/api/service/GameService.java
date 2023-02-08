package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.game.*;
import com.ssafy.a107.api.response.game.FastClickRes;
import com.ssafy.a107.api.response.game.BR31Res;
import com.ssafy.a107.api.response.game.GameOfDeathRes;
import com.ssafy.a107.common.exception.BadRequestException;
import com.ssafy.a107.common.exception.NotFoundException;

public interface GameService {
    BR31Res createBRGameSession(BR31CreateReq br31CreateReq);

    BR31Res setBR31point(BR31Req br31Req) throws NotFoundException;

    GameOfDeathRes createGameOfDeathSession(GameOfDeathCreateReq gameOfDeathCreateReq) throws BadRequestException;

    GameOfDeathRes runGameOfDeathSession(GameOfDeathReq gameOfDeathReq) throws NotFoundException;

    FastClickRes createFastClickSession(FastClickCreateReq fastClickCreateReq) throws NotFoundException;

    FastClickRes runFastClickSession(FastClickReq fastClickReq) throws NotFoundException;
}
