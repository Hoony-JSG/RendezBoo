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
    public EmotionDataRes(EmotionData emotionData){
        this.anger = emotionData.getAnger();
        this.contempt = emotionData.getContempt();
        this.disgust = emotionData.getDisgust();
        this.fear = emotionData.getFear();
        this.happiness = emotionData.getHappiness();
        this.neutral = emotionData.getNeutral();
        this.sadness = emotionData.getSadness();
        this.surprise = emotionData.getSurprise();
    }

}
