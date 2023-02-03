package com.ssafy.a107.api.request.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameOfDeathCreateReq {
    private Long multiMeetingRoomSeq;

    private Long startUserSeq;

    private Integer count;
}
