package com.ssafy.a107.db.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Notice extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(columnDefinition = "INTEGER DEFAULT 0", nullable = false)
    private int hit;

    @CreatedDate
    @JsonProperty("created_at")
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
