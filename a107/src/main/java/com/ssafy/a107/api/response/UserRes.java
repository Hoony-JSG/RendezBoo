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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRes {

    private String email;
    private String city;
    /**
     * 남자: 1 여자: 0
     */
    private Boolean gender;
    private String phoneNumber;
    private String name;
    private String profileImagePath;
    private String mbti;
    private Long point;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Badge badge;
    private List<UserInterest> userInterests = new ArrayList<>();

    public UserRes(User user) {
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
