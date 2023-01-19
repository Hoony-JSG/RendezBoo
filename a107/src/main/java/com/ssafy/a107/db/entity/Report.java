package com.ssafy.a107.db.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
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
    private String reportReason;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;}
