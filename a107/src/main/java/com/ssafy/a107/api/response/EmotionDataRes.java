package com.ssafy.a107.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class EmotionDataRes {
    private final Double anger;
    private final Double contempt;
    private final Double disgust;
    private final Double fear;
    private final Double happiness;
    private final Double neutral;
    private final Double sadness;
    private final Double surprise;

    public EmotionDataRes() {
        this.anger = 0.0;
        this.contempt = 0.0;
        this.disgust = 0.0;
        this.fear = 0.0;
        this.happiness = 0.0;
        this.neutral = 0.0;
        this.sadness = 0.0;
        this.surprise = 0.0;
    }
}
