package com.ssafy.a107.api.response;


import com.ssafy.a107.db.entity.OnetoOneMeetingRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OneToOneMeetingRoomRes {
    private Long seq;

    private String sessionId;

    private Byte status;

    private Long manSeq;

    private Long womanSeq;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public OneToOneMeetingRoomRes(OnetoOneMeetingRoom room) {
        this.seq = room.getSeq();
        this.sessionId = room.getSessionId();
        this.status = room.getStatus();
        this.manSeq = room.getManSeq();
        this.womanSeq = room.getWomanSeq();
        this.createdAt = room.getCreatedAt();
        this.updatedAt = room.getUpdatedAt();
    }

}
