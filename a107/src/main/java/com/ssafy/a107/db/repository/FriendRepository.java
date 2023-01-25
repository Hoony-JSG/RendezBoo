package com.ssafy.a107.db.repository;

import com.ssafy.a107.db.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    @Modifying
    @Transactional
    void deleteByUserSeqAndFriendSeq(Long userSeq, Long friendSeq);
}
