package com.ssafy.a107.db.repository;

import com.ssafy.a107.db.entity.OnetoOneMeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OneToOneMeetingRoomRepository extends JpaRepository<OnetoOneMeetingRoom, Long> {

    List<OnetoOneMeetingRoom> getOnetoOneMeetingRoomsByManSeqIsNullAndStatus(Byte status);

    List<OnetoOneMeetingRoom> getOnetoOneMeetingRoomsByWomanSeqIsNullAndStatus(Byte status);
}
