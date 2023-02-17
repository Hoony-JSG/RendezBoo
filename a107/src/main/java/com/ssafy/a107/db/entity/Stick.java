package com.ssafy.a107.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.HashMap;
import java.util.Map;

@RedisHash("stick")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stick {

    @Id
    private Long multiMeetingRoomSeq;

    private Map<Long, Long> targets = new HashMap<>();

    @TimeToLive
    private Long expiration;

    public void setTargets(Map<Long, Long> targets) {
        this.targets = targets;
    }
}
