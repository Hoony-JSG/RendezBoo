package com.ssafy.a107.db.repository;

import com.ssafy.a107.db.entity.EmotionData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmotionDataRepository extends JpaRepository<EmotionData, Long> {
    @Query("SELECT AVG(e.anger), AVG(e.contempt), AVG(e.disgust), AVG(e.fear), AVG(e.happiness), AVG(e.neutral), AVG(e.sadness), AVG(e.surprise) FROM EmotionData e WHERE e.user.seq = ?1 ORDER BY e.createdAt DESC")
    Optional<EmotionData> getAVGExpressionDataByUserSeq(Long userSeq);
}
