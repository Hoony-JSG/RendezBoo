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

@Document(collection = "chat")
@Getter
@NoArgsConstructor
public class MultiMeetingRoomChat {

    @Id
    @Field(value = "_id", targetType = FieldType.OBJECT_ID)
    private String seq;
    private String message;
    private Long multiMeetingRoomSeq;
    private Long senderSeq;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public MultiMeetingRoomChat(String message, Long multiMeetingRoomSeq, Long senderSeq) {
        this.message = message;
        this.multiMeetingRoomSeq = multiMeetingRoomSeq;
        this.senderSeq = senderSeq;
    }

}
