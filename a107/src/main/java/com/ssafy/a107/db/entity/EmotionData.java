package com.ssafy.a107.db.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
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

    @Builder
    public EmotionData(User user, OnetoOneMeetingRoom meetingRoom, Double anger, Double contempt, Double disgust, Double fear, Double happiness, Double neutral, Double sadness, Double surprise) {
        this.user = user;
        this.meetingRoom = meetingRoom;
        this.anger = anger;
        this.contempt = contempt;
        this.disgust = disgust;
        this.fear = fear;
        this.happiness = happiness;
        this.neutral = neutral;
        this.sadness = sadness;
        this.surprise = surprise;
    }
}
