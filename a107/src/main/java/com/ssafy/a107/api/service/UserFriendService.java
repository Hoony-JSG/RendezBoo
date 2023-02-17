package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.FriendReq;
import com.ssafy.a107.common.exception.NotFoundException;

public interface UserFriendService {

    Long addFriend(FriendReq friendReq) throws NotFoundException;

    void deleteFriend(FriendReq friendReq) throws NotFoundException;
}
