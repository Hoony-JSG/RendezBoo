package com.ssafy.a107.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StickReq {

    private Long multiMeetingRoomSeq;
    private Long userSeq;
    private Long targetSeq;
}
