package com.ssafy.a107.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EntityListeners;
import java.time.LocalDateTime;

@Document(collection = "chat")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Chat extends BaseEntity{

    private String message;
    private Long senderSeq;
    private Long receiverSeq;

    @CreatedDate
    private LocalDateTime createdAt;
}
