package com.ssafy.a107.api.service;

import com.ssafy.a107.common.exception.NotFoundException;

public interface UserFriendService {

    Long addFriend(Long userSeq, Long otherUserSeq) throws NotFoundException;

    void deleteFriend(Long userSeq, Long otherUserSeq) throws NotFoundException;
}
