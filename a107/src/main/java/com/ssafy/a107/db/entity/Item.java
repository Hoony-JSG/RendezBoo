package com.ssafy.a107.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Item extends BaseEntity {

    @Column(nullable = false)
    private Byte type;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 100)
    private String url;

    public void update(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
