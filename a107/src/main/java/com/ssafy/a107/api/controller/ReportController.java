package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.request.ReportReq;
import com.ssafy.a107.api.service.ReportService;
import com.ssafy.a107.api.service.UserService;
import com.ssafy.a107.db.entity.Report;
import com.ssafy.a107.db.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private UserService userService;

    @PostMapping("/api/report")
    public ResponseEntity<?> save(@RequestBody ReportReq req){
        Long reporterSeq = req.getReporterSeq();
        Long targetSeq = req.getTargetSeq();

        User reporter = userService.getUserBySeq(reporterSeq);
        User target = userService.getUserBySeq(targetSeq);

        Report report = Report.builder()
                .reporter(reporter)
                .target(target)
                .reportType(req.getReportType())
                .reportDetail(req.getReportDetail())
                .reportDetail(req.getReportDetail())
                .build();

        Long reportSeq = reportService.save(report);

        return ResponseEntity.status(HttpStatus.CREATED).body(reportSeq);
    }

    @GetMapping("/api/report")
    public ResponseEntity<?> findAll(){
        List<Report> all = reportService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(all);
    }
}
