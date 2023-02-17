package com.ssafy.a107.api.response;

import com.ssafy.a107.db.entity.Chat;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRes {

    private String Seq;
    private String message;
    private Long chatRoomSeq;
    private Long senderSeq;
    private Long receiverSeq;
    private LocalDateTime createdAt;

    @Builder
    public ChatRes(Chat chat) {
        this.Seq = chat.getSeq();
        this.message = chat.getMessage();
        this.chatRoomSeq = chat.getChatRoomSeq();
        this.senderSeq = chat.getSenderSeq();
        this.receiverSeq = chat.getReceiverSeq();
        this.createdAt = chat.getCreatedAt();
    }
}
