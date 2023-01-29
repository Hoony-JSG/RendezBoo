package com.ssafy.a107.api.service;

import com.ssafy.a107.common.exception.NotFoundException;

public interface UserBlockedService {

    Long addBlockedUser (Long userSeq, Long targetUserSeq) throws NotFoundException;
}
