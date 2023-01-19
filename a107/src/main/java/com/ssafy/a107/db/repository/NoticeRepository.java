package com.ssafy.a107.db.repository;

import com.ssafy.a107.db.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
