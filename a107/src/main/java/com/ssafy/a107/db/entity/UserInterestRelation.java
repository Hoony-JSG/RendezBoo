package com.ssafy.a107.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@IdClass(UserInterestSeq.class)
@Getter
public class UserInterestRelation extends UserInterestSeq{

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    @JsonIgnore
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_seq")
    @JsonIgnore
    private Interest interest;
}
