package com.ssafy.a107.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmotionDataReq {
    private Double anger;
    private Double contempt;
    private Double disgust;
    private Double fear;
    private Double happiness;
    private Double neutral;
    private Double sadness;
    private Double surprise;

    private Long meetingRoomSeq;
    private Long userSeq;

    public Double[] emotionDataArr() {
        return new Double[] {anger, disgust, fear, happiness, sadness, surprise};
    }
}
