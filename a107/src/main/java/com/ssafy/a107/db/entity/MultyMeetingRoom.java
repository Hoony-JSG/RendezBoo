package com.ssafy.a107.db.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;
@Entity
@Getter
@NoArgsConstructor
public class MultyMeetingRoom extends BaseEntity {
    @Column
    private String title;
    @Column(nullable = false)
    private Byte status;

    @CreatedDate
    @JsonProperty("created_at")
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public MultyMeetingRoom(String title, Byte status, Long manNum, Long womanNum) {
        this.title = title;
        this.status = (byte)0;
    }
}
