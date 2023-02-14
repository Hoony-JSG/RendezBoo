package com.ssafy.a107.db.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;


@Getter
@NoArgsConstructor
@Entity
public class Badge extends BaseEntity {

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, length = 100)
    private String url;

    @Builder
    public Badge(String name, String url, String description) {
        this.name = name;
        this.url = url;
        this.description = description;
    }

    public void update(String name, String url, String description){
        this.name = name;
        this.url = url;
        this.description = description;
    }
}
