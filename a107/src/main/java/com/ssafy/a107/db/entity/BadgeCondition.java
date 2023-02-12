package com.ssafy.a107.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BadgeCondition extends BaseEntity {

    @Column
    @JoinColumn(name = "user_seq", columnDefinition = "INT UNSIGNED")
    private Long userSeq;

    @Column
    @JoinColumn(name = "fin_count")
    private Integer finCount;

    @Column
    @JoinColumn(name = "item_count")
    private Integer itemCount;

    @Column
    @JoinColumn(name = "anger_count")
    private Integer angerCount;

    @Column
    @JoinColumn(name = "contempt_count")
    private Integer contemptCount;

    @Column
    @JoinColumn(name = "disgust_count")
    private Integer disgustCount;

    @Column
    @JoinColumn(name = "fear_count")
    private Integer fearCount;

    @Column
    @JoinColumn(name = "happiness_count")
    private Integer happinessCount;

    @Column
    @JoinColumn(name = "neutral_count")
    private Integer neutralCount;

    @Column
    @JoinColumn(name = "sadness_count")
    private Integer sadnessCount;

    @Column
    @JoinColumn(name = "surprise_count")
    private Integer surpriseCount;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column
    private LocalDateTime updatedAt;

    public BadgeCondition(Long userSeq) {
        this.userSeq = userSeq;
        finCount = itemCount = angerCount = contemptCount = disgustCount = fearCount = happinessCount
                = neutralCount = sadnessCount = surpriseCount = 0;
    }

    public Integer addFinCount() {
        return ++finCount;
    }

    public Integer addItemCount() {
        return ++itemCount;
    }

    public Integer addAngerCount() {
        return ++angerCount;
    }

    public Integer addContemptCount() {
        return ++contemptCount;
    }

    public Integer addDisgustCount() {
        return ++disgustCount;
    }

    public Integer addFearCount() {
        return ++fearCount;
    }

    public Integer addHappinessCount() {
        return ++happinessCount;
    }

    public Integer addNeutralCount() {
        return ++neutralCount;
    }

    public Integer addSadnessCount() {
        return ++sadnessCount;
    }

    public Integer addSurpriseCount() {
        return ++surpriseCount;
    }
}
