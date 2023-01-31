package com.ssafy.a107.api.response;

import com.ssafy.a107.db.entity.ChatRoom;
import com.ssafy.a107.db.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomRes {

    private Long seq;
    private User userMale;
    private User userFemale;
    private LocalDateTime createdAt;

    @Builder
    public ChatRoomRes(ChatRoom chatRoom) {
        this.seq = chatRoom.getSeq();
        this.userMale = chatRoom.getUserMale();
        this.userFemale = chatRoom.getUserFemale();
        this.createdAt = chatRoom.getCreatedAt();
    }
}
