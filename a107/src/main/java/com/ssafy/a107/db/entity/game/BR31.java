package com.ssafy.a107.db.entity.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.List;

@Getter
@RedisHash("br31")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BR31 {

    @Id
    private Long multiMeetingRoomSeq;

    private Integer point;

    private List<Long> order;

    private Long nowUser;

    @TimeToLive
    private Long expiration;

    public void addPoint(int point) {
        this.point += point;
    }

    public void setNextUser(Long userSeq) {
        this.nowUser = userSeq;
    }

    public void setOrder(List<Long> order) {
        this.order = order;
    }
}
