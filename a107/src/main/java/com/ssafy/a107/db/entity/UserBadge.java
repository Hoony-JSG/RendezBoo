package com.ssafy.a107.db.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@IdClass(UserBadgeSeq.class)
public class UserBadge {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_seq")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "badge_seq")
    private Badge badge;
}
