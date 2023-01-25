package com.ssafy.a107.api.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Getter
@NoArgsConstructor
public class ReportReq {
    private Long reporterSeq;
    private Long targetSeq;
    private byte reportType;
    private String reportDetail;

    @Builder
    public ReportReq(Long reporterSeq, Long targetSeq, byte reportType, String reportDetail) {
        this.reporterSeq = reporterSeq;
        this.targetSeq = targetSeq;
        this.reportType = reportType;
        this.reportDetail = reportDetail;
    }
}