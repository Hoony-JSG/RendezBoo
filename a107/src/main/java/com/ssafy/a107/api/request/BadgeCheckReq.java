package com.ssafy.a107.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BadgeCheckReq {

    private Boolean finishedMeeting;
    private Boolean boughtItem;
    private String top1Emotion;
}
