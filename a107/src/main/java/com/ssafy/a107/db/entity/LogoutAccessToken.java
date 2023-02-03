package com.ssafy.a107.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import javax.persistence.Id;

@Getter
@RedisHash("logoutAccessToken")
@AllArgsConstructor
@Builder
public class LogoutAccessToken {

    @Id
    private String id;

    private String userEmail;

    @TimeToLive
    private Long expiration;

    public static LogoutAccessToken of(String accessToken, String userEmail, Long remainMilliSeconds) {
        return LogoutAccessToken.builder()
                .id(accessToken)
                .userEmail(userEmail)
                .expiration(remainMilliSeconds / 1000)
                .build();
    }
}
