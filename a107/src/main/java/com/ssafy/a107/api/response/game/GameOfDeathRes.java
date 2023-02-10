package com.ssafy.a107.api.response.game;


import com.ssafy.a107.api.response.MultiChatFlag;
import com.ssafy.a107.db.entity.game.GameOfDeath;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameOfDeathRes {

    private MultiChatFlag flag;

    private Long multiMeetingRoomSeq;

    private List<Long> resultList;

    private Map<Long, Long> targets;

    private Long loseUserSeq;

    private String message;

    private Set<Long> rechoiceSet;

    public GameOfDeathRes(GameOfDeath gameOfDeath, List<Long> resultList, Long loseUserSeq, String message, MultiChatFlag flag, Set<Long> rechoiceSet) {
        this.multiMeetingRoomSeq = gameOfDeath.getMultiMeetingRoomSeq();
        this.targets = gameOfDeath.getTargets();
        this.resultList = resultList;
        this.loseUserSeq = loseUserSeq;
        this.message = message;
        this.flag = flag;
        this.rechoiceSet = rechoiceSet;
    }
}
