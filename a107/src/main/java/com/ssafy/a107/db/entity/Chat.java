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
public class Chat{

    @Id
    @Field(value = "_id", targetType = FieldType.OBJECT_ID)
    private String seq;
    private String message;
    private Long chatRoomSeq;
    private Long senderSeq;
    private Long receiverSeq;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public Chat(String message, Long chatRoomSeq, Long senderSeq, Long receiverSeq) {
        this.message = message;
        this.chatRoomSeq = chatRoomSeq;
        this.senderSeq = senderSeq;
        this.receiverSeq = receiverSeq;
    }
}
