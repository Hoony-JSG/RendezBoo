package com.ssafy.a107.db.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;
@Entity
@Getter
public class ManyToManyMeetingRoom extends BaseEntity {
    @Column(nullable = false)
    private int meetingRootSeq;
    @Column(nullable = false)
    private Byte status;
    private int man1Seq;
    private int woman1Seq;
    private int man2Seq;
    private int woman2Seq;
    private int man3Seq;
    private int woman3Seq;

    @CreatedDate
    @JsonProperty("created_at")
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
