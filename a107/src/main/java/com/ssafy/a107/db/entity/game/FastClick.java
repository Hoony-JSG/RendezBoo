package com.ssafy.a107.db.entity.game;

import com.ssafy.a107.api.request.game.FastClickReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "fastclick")
@Getter
@NoArgsConstructor
public class FastClick {
    @Id
    @Field(value = "_id", targetType = FieldType.OBJECT_ID)
    private Long sessionId;
    private Long multiMeetingRoomSeq;

    private List<Long> users;
    private Map<Long, Integer> point = new HashMap<>();
    private Integer count = 0;

    public void getPoint(FastClickReq fastClickReq){
        if(count<5){
            point.put(fastClickReq.getUserSeq(), fastClickReq.getPoint());
        }
    }

    @Builder
    public FastClick(Long sessionId, Long multiMeetingRoomSeq, List<Long> users) {
        this.sessionId = sessionId;
        this.multiMeetingRoomSeq = multiMeetingRoomSeq;
        this.users = users;
    }
}
