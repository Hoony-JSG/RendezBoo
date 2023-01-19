package com.ssafy.a107.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter @Setter
public class Item extends BaseEntity {

    @Column(nullable = false)
    private byte type;

    @Column(nullable = false)
    private String url;
}
