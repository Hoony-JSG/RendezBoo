package com.ssafy.a107.db.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class EmotionData extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "user_seq", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "meeting_room_seq", nullable = false)
    private OnetoOneMeetingRoom meetingRoom;
    @Column(nullable = false)
    private Double anger;
    @Column(nullable = false)
    private Double contempt;
    @Column(nullable = false)
    private Double disgust;
    @Column(nullable = false)
    private Double fear;
    @Column(nullable = false)
    private Double happiness;
    @Column(nullable = false)
    private Double neutral;
    @Column(nullable = false)
    private Double sadness;
    @Column(nullable = false)
    private Double surprise;
    @CreatedDate
    @JsonProperty("created_at")
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
