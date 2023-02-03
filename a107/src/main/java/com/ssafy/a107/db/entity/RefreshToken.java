package com.ssafy.a107.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import javax.persistence.Id;

@Getter
@RedisHash("refreshToken")
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    private String id;

    private String refreshToken;

    @TimeToLive
    private Long expiration;

    public static RefreshToken createRefreshToken(String userEmail, String refreshToken, Long remainMilliSeconds) {
        return RefreshToken.builder()
                .id(userEmail)
                .refreshToken(refreshToken)
                .expiration(remainMilliSeconds / 1000)
                .build();
    }
}
