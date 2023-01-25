package com.ssafy.a107.api.service;

import com.ssafy.a107.db.entity.Report;
import com.ssafy.a107.db.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final ReportRepository reportRepository;

    @Transactional
    public Long save(Report report){
        return reportRepository.save(report).getSeq();
    }
    public List<Report> findAll(){
        return reportRepository.findAll();
    }
}
