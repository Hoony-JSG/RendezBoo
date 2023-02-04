package com.ssafy.a107.db.repository;

import com.ssafy.a107.db.entity.MultiMeetingRoom;
import com.ssafy.a107.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MultiMeetingRoomRepository extends JpaRepository<MultiMeetingRoom, Long> {

    @Query("select u from MultiMeetingRoomUser mu inner join User u on mu.user.seq = u.seq where mu.multiMeetingRoom.seq = ?1")
    List<User> findUsersByMultiMeetingRoomSeq(Long roomSeq);

    @Query("select u.seq from MultiMeetingRoomUser mu inner join User u on mu.user.seq = u.seq where mu.multiMeetingRoom.seq = ?1")
    List<Long> findUserSequencesByMultiMeetingRoomSeq(Long roomSeq);

    @Query("select count(*) from MultiMeetingRoomUser mu where mu.multiMeetingRoom.seq=?1 and mu.user.gender=?2")
    Long countByMultiMeetingRoomSeqAndGender(Long multiMeetingRoomSeq, Boolean gender);
}
