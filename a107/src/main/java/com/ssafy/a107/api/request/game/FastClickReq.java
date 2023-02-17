package com.ssafy.a107.api.request.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FastClickReq {

    private Long multiMeetingRoomSeq;
    private Long userSeq;
    private Integer score;
}
