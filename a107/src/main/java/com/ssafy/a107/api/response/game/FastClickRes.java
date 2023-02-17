package com.ssafy.a107.api.response.game;

import com.ssafy.a107.api.response.MultiChatFlag;
import com.ssafy.a107.db.entity.game.FastClick;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FastClickRes {

    private MultiChatFlag flag;
    private Long multiMeetingRoomSeq;
    private String message;
    private Map<Long, Integer> scores;
    private Long loseUserSeq;
    private GameType gameType;

    public FastClickRes(FastClick fastClick, Long loseUserSeq, String message, MultiChatFlag flag) {
        this.flag = flag;
        this.multiMeetingRoomSeq = fastClick.getMultiMeetingRoomSeq();
        this.message = message;
        this.scores = fastClick.getScores();
        this.loseUserSeq = loseUserSeq;
        this.gameType = GameType.FASTCLICK;
    }
}
