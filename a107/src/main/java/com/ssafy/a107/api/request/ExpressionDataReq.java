package com.ssafy.a107.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExpressionDataReq {
    private double emotion1;
    private double emotion2;
    private double emotion3;
    private double emotion4;
    private double emotion5;
    private double emotion6;

    private Long meeting_room_seq;
    private Long user_seq;
    @Builder
    public ExpressionDataReq(double emotion1, double emotion2, double emotion3, double emotion4, double emotion5, double emotion6, Long meeting_room_seq, Long user_seq) {
        this.emotion1 = emotion1;
        this.emotion2 = emotion2;
        this.emotion3 = emotion3;
        this.emotion4 = emotion4;
        this.emotion5 = emotion5;
        this.emotion6 = emotion6;
        this.meeting_room_seq = meeting_room_seq;
        this.user_seq = user_seq;
    }
}
