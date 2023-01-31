package com.ssafy.a107.db.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ManyToManyMeetingRoom extends BaseEntity {
    @Column(nullable = false)
    private int meetingRootSeq;
    @Column(nullable = false)
    private Byte status;

    @Column(nullable = true, columnDefinition = "INT UNSIGNED")
    private Long man1Seq;

    @Column(nullable = true, columnDefinition = "INT UNSIGNED")
    private Long woman1Seq;

    @Column(nullable = true, columnDefinition = "INT UNSIGNED")
    private Long man2Seq;

    @Column(nullable = true, columnDefinition = "INT UNSIGNED")
    private Long woman2Seq;

    @Column(nullable = true, columnDefinition = "INT UNSIGNED")
    private Long man3Seq;

    @Column(nullable = true, columnDefinition = "INT UNSIGNED")
    private Long woman3Seq;

    @CreatedDate
    @JsonProperty("created_at")
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
