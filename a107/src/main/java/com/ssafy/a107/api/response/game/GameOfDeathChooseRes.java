package com.ssafy.a107.api.response.game;

import com.ssafy.a107.api.request.game.GameOfDeathReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameOfDeathChooseRes {

    private Long multiMeetingRoomSeq;
    private Long userSeq;
    private Long targetSeq;
    private String message;

    public GameOfDeathChooseRes(GameOfDeathReq req, String message) {
        this.multiMeetingRoomSeq = req.getMultiMeetingRoomSeq();
        this.userSeq = req.getUserSeq();
        this.targetSeq = req.getTargetSeq();
        this.message = message;
    }
}
