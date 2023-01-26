package com.ssafy.a107.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Report extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_seq")
    private User reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_seq")
    private User target;

    @Column
    private byte reportType;

    @Column(columnDefinition = "TEXT")
    private String reportDetail;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public Report(User reporter, User target, byte reportType, String reportDetail) {
        this.reporter = reporter;
        this.target = target;
        this.reportType = reportType;
        this.reportDetail = reportDetail;
    }
}
