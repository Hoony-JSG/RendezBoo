package com.ssafy.a107.db.repository;

import com.ssafy.a107.db.entity.EmotionData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmotionDataRepository extends JpaRepository<EmotionData, Long> {

    //SELECT AVG(e1),AVG(e2),AVG(e3),AVG(e4),AVG(e5),AVG(e6),AVG(e7),AVG(e8)
    //FROM emotion_data
    //WHERE user_seq = "aaa"
    //ORDER BY created_time desc;
    @Query("SELECT AVG(anger),AVG(contempt),AVG(disgust),AVG(fear)," +
            "AVG(happiness),AVG(neutral),AVG(sadness),AVG(surprise) "+
            "FROM emotion_data WHERE user_seq = {userSeq}"+
            "ORDER BY created_time DESC")
    EmotionData getAVGExpressionDataByUserSeq(Long userSeq);
}
