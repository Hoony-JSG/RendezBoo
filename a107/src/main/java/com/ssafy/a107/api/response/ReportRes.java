package com.ssafy.a107.api.response;

import com.ssafy.a107.db.entity.Report;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ReportRes {
    private final Long reporterSeq;
    private final Long targetSeq;
    private final byte reportType;
    private final String reportDetail;
    private final LocalDateTime createdAt;
    public ReportRes(Report report){
        this.reporterSeq = report.getReporter().getSeq();
        this.targetSeq = report.getTarget().getSeq();
        this.reportType = report.getReportType();
        this.reportDetail = report.getReportDetail();
        this.createdAt = report.getCreatedAt();
    }
}