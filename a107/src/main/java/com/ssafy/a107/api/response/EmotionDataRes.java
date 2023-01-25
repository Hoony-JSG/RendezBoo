package com.ssafy.a107.api.response;

import com.ssafy.a107.db.entity.EmotionData;
import lombok.Getter;

@Getter
public class EmotionDataRes {
    private final Double anger;
    private final Double contempt;
    private final Double disgust;
    private final Double fear;
    private final Double happiness;
    private final Double neutral;
    private final Double sadness;
    private final Double surprise;
    public EmotionDataRes(EmotionData expressionData){
        this.anger = expressionData.getAnger();
        this.contempt = expressionData.getContempt();
        this.disgust = expressionData.getDisgust();
        this.fear = expressionData.getFear();
        this.happiness = expressionData.getHappiness();
        this.neutral = expressionData.getNeutral();
        this.sadness = expressionData.getSadness();
        this.surprise = expressionData.getSurprise();
    }

}
