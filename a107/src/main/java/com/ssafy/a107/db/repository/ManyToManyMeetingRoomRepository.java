package com.ssafy.a107.db.repository;

import com.ssafy.a107.db.entity.ManyToManyMeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManyToManyMeetingRoomRepository extends JpaRepository<ManyToManyMeetingRoom, Long> {
}
