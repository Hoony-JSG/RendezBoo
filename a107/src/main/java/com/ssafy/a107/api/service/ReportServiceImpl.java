package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.ReportReq;
import com.ssafy.a107.api.response.ReportRes;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.db.entity.Report;
import com.ssafy.a107.db.entity.User;
import com.ssafy.a107.db.repository.ReportRepository;
import com.ssafy.a107.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService{

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    @Transactional
    public Long save(ReportReq req) throws NotFoundException {
        Report newReport = Report.builder()
                .reporter(userRepository.findById(req.getReporterSeq())
                        .orElseThrow(() -> new NotFoundException("Wrong User Seq!")))
                .target(userRepository.findById(req.getTargetSeq())
                        .orElseThrow(() -> new NotFoundException("Wrong User Seq!")))
                .reportType(req.getReportType())
                .reportDetail(req.getReportDetail())
                .build();
        return reportRepository.save(newReport).getSeq();
    }
    public List<ReportRes> findAll(){
        List<Report> entityAll = reportRepository.findAll();
        List<ReportRes> resAll = new ArrayList<>();
        for(Report r: entityAll) resAll.add(new ReportRes(r));
        return resAll;
    }
}
