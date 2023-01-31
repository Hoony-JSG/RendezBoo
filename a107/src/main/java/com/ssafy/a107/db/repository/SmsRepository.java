package com.ssafy.a107.db.repository;

import com.ssafy.a107.db.entity.Sms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SmsRepository extends JpaRepository<Sms, Long> {

    Optional<Sms> findTopByEmailOrderByCreatedAtDesc(String email);
}
