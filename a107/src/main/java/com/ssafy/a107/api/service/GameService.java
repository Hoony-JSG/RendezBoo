package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.game.BR31CreateReq;
import com.ssafy.a107.api.request.game.BR31Req;
import com.ssafy.a107.api.response.game.BR31Res;

public interface GameService {
    public String createBRGameSession(BR31CreateReq br31CreateReq);

    public BR31Res setBR31point(BR31Req br31Req);
}
