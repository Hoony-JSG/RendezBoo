package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.StickCreateReq;
import com.ssafy.a107.api.request.StickReq;
import com.ssafy.a107.api.response.StickRes;
import com.ssafy.a107.common.exception.NotFoundException;

public interface StickService {

    StickRes createStickOfLove(StickCreateReq stickCreateReq);
    StickRes runStickOfLove(StickReq stickReq) throws NotFoundException;
}
