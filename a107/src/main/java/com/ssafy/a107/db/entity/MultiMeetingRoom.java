package com.ssafy.a107.db.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import java.time.LocalDateTime;
@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class MultiMeetingRoom extends BaseEntity {
    @Column(length = 50)
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
