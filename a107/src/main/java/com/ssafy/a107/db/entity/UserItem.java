package com.ssafy.a107.db.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@IdClass(UserItemId.class)
public class UserItem {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_seq")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "item_seq")
    private Item item;
}