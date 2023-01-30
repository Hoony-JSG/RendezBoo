package com.ssafy.a107.api.response;

import com.ssafy.a107.db.entity.Badge;
import com.ssafy.a107.db.entity.User;
import com.ssafy.a107.db.entity.UserInterest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class UserRes {

    private final Long seq;
    private final String email;
    private final String city;
    /**
     * 남자: 1 여자: 0
     */
    private final Boolean gender;
    private final String phoneNumber;
    private final String name;
    private final String profileImagePath;
    private final String mbti;
    private final Long point;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final Badge badge;
    private final List<UserInterest> userInterests;

    public UserRes(User user) {
        this.seq = user.getSeq();
        this.email = user.getEmail();
        this.city = user.getCity();
        this.gender = user.getGender();
        this.phoneNumber = user.getPhoneNumber();
        this.name = user.getName();
        this.profileImagePath = user.getProfileImagePath();
        this.mbti = user.getMbti();
        this.point = user.getPoint();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        this.badge = user.getBadge();
        this.userInterests = user.getUserInterests();
    }
}
