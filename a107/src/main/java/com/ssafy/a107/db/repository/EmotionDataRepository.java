package com.ssafy.a107.db.repository;

import com.ssafy.a107.db.entity.EmotionData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmotionDataRepository extends JpaRepository<EmotionData, Long> {
    List<EmotionData> findTop10ByUserSeq(Long userSeq);
}
