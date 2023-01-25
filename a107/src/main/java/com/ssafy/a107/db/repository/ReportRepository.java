package com.ssafy.a107.db.repository;

import com.ssafy.a107.db.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository  extends JpaRepository<Report, Long> {
}
