package com.ssafy.a107.db.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Getter
public class ExpressionData extends BaseEntity{
    private int userSeq;
    private int meetingRoomSeq;
    @Column(nullable = false)
    private double emotion1;
    @Column(nullable = false)
    private double emotion2;
    @Column(nullable = false)
    private double emotion3;
    @Column(nullable = false)
    private double emotion4;
    @Column(nullable = false)
    private double emotion5;
    @Column(nullable = false)
    private double emotion6;
    @CreatedDate
    @JsonProperty("created_at")
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
