package com.ssafy.a107.api.service;

public interface FriendService {

    void addFriend(Long userSeq, Long otherUserSeq);

    void deleteFriend(Long userSeq, Long otherUserSeq);
}
