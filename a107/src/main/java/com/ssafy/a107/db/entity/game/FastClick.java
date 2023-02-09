package com.ssafy.a107.db.entity.game;

import com.ssafy.a107.api.request.game.FastClickReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RedisHash("fastclick")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FastClick {

    @Id
    private Long multiMeetingRoomSeq;

    private List<Long> users;

    private Map<Long, Integer> scores = new HashMap<>();

    private Integer count = 0;

    @TimeToLive
    private Long expiration;

    public void addCount() {
        this.count++;
    }
}
