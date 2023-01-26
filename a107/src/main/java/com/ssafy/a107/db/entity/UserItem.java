package com.ssafy.a107.db.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@IdClass(UserItemSeq.class)
public class UserItem {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_seq", columnDefinition = "INT UNSIGNED")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "item_seq", columnDefinition = "INT UNSIGNED")
    private Item item;
}