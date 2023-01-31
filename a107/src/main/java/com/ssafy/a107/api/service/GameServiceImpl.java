package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.game.BR31CreateReq;
import com.ssafy.a107.api.request.game.BR31Req;
import com.ssafy.a107.api.response.game.BR31Res;
import com.ssafy.a107.db.entity.game.BR31;
import com.ssafy.a107.db.repository.game.BR31Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final BR31Repository br31Repository;

    @Override
    public String createBRGameSession(BR31CreateReq br31CreateReq) {
        
        return null;
    }

    @Override
    public BR31Res setBR31point(BR31Req br31Req) {
        return null;
    }
}
