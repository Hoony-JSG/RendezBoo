package com.ssafy.a107.db.entity.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.HashMap;
import java.util.Map;

@Getter
@RedisHash("god")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameOfDeath {

    @Id
    private Long multiMeetingRoomSeq;

    private Map<Long, Long> targets = new HashMap<>();

    private Long startUserSeq;

    private Integer count;

    @TimeToLive
    private Long expiration;
}
