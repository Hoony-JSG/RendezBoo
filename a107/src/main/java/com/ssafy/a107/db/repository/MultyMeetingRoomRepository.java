package com.ssafy.a107.db.repository;

import com.ssafy.a107.db.entity.MultyMeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MultyMeetingRoomRepository extends JpaRepository<MultyMeetingRoom, Long> {
    List<MultyMeetingRoom> getMultyMeetingRoomBySeq(Long meetingRoomSeq);
}
