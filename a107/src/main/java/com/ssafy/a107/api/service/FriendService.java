package com.ssafy.a107.api.service;

public interface FriendService {

    Long addFriend(Long userSeq, Long otherUserSeq);

    void deleteFriend(Long userSeq, Long otherUserSeq);
}
