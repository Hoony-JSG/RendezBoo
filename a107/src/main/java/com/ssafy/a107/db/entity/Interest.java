package com.ssafy.a107.db.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
public class Interest extends BaseEntity {

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    private byte type;
}
