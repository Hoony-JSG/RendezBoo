package com.ssafy.a107.api.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Getter
@NoArgsConstructor
public class ReportReq {
    private int reporterSeq;
    private int targetSeq;
    private String reportType;
    private String reportDetail;

    @Builder
    public ReportReq(int reporterSeq, int targetSeq, String reportType, String reportDetail) {
        this.reporterSeq = reporterSeq;
        this.targetSeq = targetSeq;
        this.reportType = reportType;
        this.reportDetail = reportDetail;
    }
}
