package com.ssafy.a107.db.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Getter
public class OnetoOneMeetingRoom extends BaseEntity{
    @Column(nullable = false)
    private int meetingRootSeq;
    @Column(nullable = false)
    private Byte status;

    private int manSeq;
    private int womanSeq;

    @CreatedDate
    @JsonProperty("created_at")
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
