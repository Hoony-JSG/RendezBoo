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

    @Column(nullable = false, length = 100)
    private String url;

    @Builder
    public Badge(String name, String url) {
        this.name = name;
        this.url = url;
    }
    public void update(String name, String url){
        this.name = name;
        this.url = url;
    }

}
