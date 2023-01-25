package com.ssafy.a107.db.repository;

import com.ssafy.a107.db.entity.EmotionData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmotionDataRepository extends JpaRepository<EmotionData, Long> {
    @Query()
    EmotionData getAvgExpressionDataByUserSeq(Long userSeq);
}
