package com.ssafy.a107.api.response;

import com.ssafy.a107.db.entity.Chat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@Builder
public class ChatRes {

    private final String Seq;
    private final String message;
    private final Long chatRoomSeq;
    private final Long senderSeq;
    private final Long receiverSeq;
    private final LocalDateTime createdAt;

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
