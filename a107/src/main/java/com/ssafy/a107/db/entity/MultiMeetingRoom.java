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
public class MultiMeetingRoom extends BaseEntity {
    @Column(length = 30)
    private String sessionId;
    @Column
    private String title;
    @Column(nullable = false)
    private Byte status = 0;

    @CreatedDate
    @JsonProperty("created_at")
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public MultiMeetingRoom(String title, Byte status) {
        this.title = title;
        this.status = status;
    }
    public void createSession(String sessionId) {
        this.sessionId = sessionId;
    }

    public void changeStatus(Byte status) {
        this.status = status;
    }
}
