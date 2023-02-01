package com.ssafy.a107.api.request.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BR31CreateReq {
    private Long multiMeetingRoomSeq;
    private Long userSeq;
}
