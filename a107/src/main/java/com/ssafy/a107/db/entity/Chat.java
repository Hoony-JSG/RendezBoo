package com.ssafy.a107.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EntityListeners;
import java.time.LocalDateTime;

@Document(collection = "chat")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Chat{

    @Id
    private Long seq;
    private String message;
    private Long senderSeq;
    private Long receiverSeq;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public Chat(String message, Long senderSeq, Long receiverSeq) {
        this.message = message;
        this.senderSeq = senderSeq;
        this.receiverSeq = receiverSeq;
    }
}
