package com.ssafy.a107.db.entity.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document(collection = "gameOfDeath")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameOfDeath {

    private Long gameId;

    private Long multiMeetingRoomSeq;

    private Map<Long, Long> targets;

    private Long startUserSeq;

    private Integer count;

    public void updateTargets(Map<Long, Long> targets) {
        this.targets = targets;
    }

}
