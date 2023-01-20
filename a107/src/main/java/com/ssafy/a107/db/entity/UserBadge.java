package com.ssafy.a107.db.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
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
