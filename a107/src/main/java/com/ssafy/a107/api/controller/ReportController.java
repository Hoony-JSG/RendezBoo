package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.request.ReportReq;
import com.ssafy.a107.api.response.ReportRes;
import com.ssafy.a107.api.service.ReportService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "신고 API", tags = {"Report"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    @PostMapping()
    public ResponseEntity<?> save(@RequestBody ReportReq req){
        Long reportSeq = reportService.save(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(reportSeq);
    }

    @GetMapping()
    public ResponseEntity<?> findAll(){
        List<ReportRes> all = reportService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(all);
    }
}
