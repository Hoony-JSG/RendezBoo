package com.ssafy.a107.db.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
public class Game extends BaseEntity {

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 300)
    private String description;
}
