package com.ssafy.a107.api.response;

import com.ssafy.a107.db.entity.ExpressionData;
import lombok.Getter;

@Getter
public class ExpressionDataRes {
    private final double emotion1;
    private final double emotion2;
    private final double emotion3;
    private final double emotion4;
    private final double emotion5;
    private final double emotion6;
    public ExpressionDataRes(ExpressionData expressionData){
        this.emotion1 = expressionData.getEmotion1();
        this.emotion2 = expressionData.getEmotion1();
        this.emotion3 = expressionData.getEmotion1();
        this.emotion4 = expressionData.getEmotion1();
        this.emotion5 = expressionData.getEmotion1();
        this.emotion6 = expressionData.getEmotion1();
    }

}
