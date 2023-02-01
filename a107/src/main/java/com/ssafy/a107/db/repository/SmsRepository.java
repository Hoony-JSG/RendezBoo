package com.ssafy.a107.db.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@RequiredArgsConstructor
@Repository
public class SmsRepository {

    private final String PREFIX = "sms:";
    private final int TIME_LIMIT = 60; // for test

    private final StringRedisTemplate stringRedisTemplate;
    public void createSmsRedis(String phoneNumber, String code) {
        stringRedisTemplate.opsForValue().set(PREFIX + phoneNumber, code, Duration.ofSeconds(TIME_LIMIT));
    }

    public String getSmsRedis(String phoneNumber) {
        return stringRedisTemplate.opsForValue().get(PREFIX + phoneNumber);
    }

    public void removeSmsRedis(String phoneNumber) {
        stringRedisTemplate.delete(PREFIX + phoneNumber);
    }

    public boolean hasKey(String phoneNumber) {
        return stringRedisTemplate.hasKey(PREFIX + phoneNumber);
    }
}
