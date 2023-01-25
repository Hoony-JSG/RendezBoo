package com.ssafy.a107.api.service;

public interface BlockedUserService {

    void addBlockedUser (Long userSeq, Long targetUserSeq);
}
