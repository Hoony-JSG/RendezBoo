package com.ssafy.a107.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BadgeCheckRes {

    private List<BadgeRes> obtainedBadges;
    private Long obtainedPoints;
}
