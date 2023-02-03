package com.ssafy.a107.db.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;

@Document(collection = "onechat")
@Getter
@NoArgsConstructor
public class OneToOneMeetingRoomChat {

    @Id
    @Field(value = "_id", targetType = FieldType.OBJECT_ID)
    private String seq;
    private String message;
    private Long oneToOneMeetingRoomSeq;
    private Long senderSeq;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public OneToOneMeetingRoomChat(String message, Long oneToOneMeetingRoomSeq, Long senderSeq) {
        this.message = message;
        this.oneToOneMeetingRoomSeq = oneToOneMeetingRoomSeq;
        this.senderSeq = senderSeq;
    }

}
