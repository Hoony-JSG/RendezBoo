package com.ssafy.a107.api.response;

import com.ssafy.a107.db.entity.Badge;
import com.ssafy.a107.db.entity.UserInterestRelation;
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

    private String password;

    private String city;

    /**
     * 남자: 1 여자: 0
     */
    private Boolean gender;

    private String phoneNumber;

    private String name;

    private String profileImagePath;

    private String MBTI;

    private Long point;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Badge badge;

    private List<UserInterestRelation> userInterestRelations = new ArrayList<>();
}
