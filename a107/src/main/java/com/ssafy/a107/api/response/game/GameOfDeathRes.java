package com.ssafy.a107.api.response.game;


import com.ssafy.a107.db.entity.game.GameOfDeath;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameOfDeathRes {
    private Long gameId;

    private Long multiMeetingRoomSeq;

    private List<Long> countingList;

    private Map<Long, Long> targets;

    private Long loseUserSeq;

    private Integer count;

    public GameOfDeathRes(GameOfDeath gameOfDeath, List<Long> countingList, Long loseUserSeq) {
        this.gameId = gameOfDeath.getGameId();
        this.multiMeetingRoomSeq = gameOfDeath.getMultiMeetingRoomSeq();
        this.targets = gameOfDeath.getTargets();
        this.countingList = countingList;
        this.loseUserSeq = loseUserSeq;
        this.count = gameOfDeath.getCount();
    }
}
