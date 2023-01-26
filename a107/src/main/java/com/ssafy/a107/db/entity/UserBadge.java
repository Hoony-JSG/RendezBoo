package com.ssafy.a107.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(UserBadgeSeq.class)
public class UserBadge {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_seq", columnDefinition = "INT UNSIGNED")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "badge_seq", columnDefinition = "INT UNSIGNED")
    private Badge badge;
}
