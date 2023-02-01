package com.ssafy.a107.db.repository;

import com.ssafy.a107.db.entity.MultiMeetingRoomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface MultiMeetingRoomUserRepository extends JpaRepository<MultiMeetingRoomUser, Long> {
    @Modifying
    @Transactional
    void deleteByMultiMeetingRoomSeqAndUserSeq(Long multiMeetingRoomSeq, Long userSeq);
}
