package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.ReportReq;
import com.ssafy.a107.api.response.ReportRes;

import java.util.List;

public interface ReportService {
    Long save(ReportReq req);
    List<ReportRes> findAll();
}
