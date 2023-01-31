package com.ssafy.a107.db.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
public class OnetoOneMeetingRoom extends BaseEntity {

    @Column(length = 30)
    private String sessionId;

    @Column(nullable = false)
    private Byte status = 0;

    @Column(nullable = true, columnDefinition = "INT UNSIGNED")
    private Long manSeq;

    @Column(nullable = true, columnDefinition = "INT UNSIGNED")
    private Long womanSeq;

    @CreatedDate
    @JsonProperty("created_at")
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    public void createSession(String sessionId) {
        this.sessionId = sessionId;
    }

    public void changeStatus(Byte status) {
        this.status = status;
    }

    public void addMan(Long manSeq) {
        this.manSeq = manSeq;
    }

    public void addWoman(Long womanSeq) {
        this.womanSeq = womanSeq;
    }
}
