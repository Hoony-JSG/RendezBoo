package com.ssafy.a107.api.response;

import com.ssafy.a107.db.entity.ChatRoom;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomRes {

    private Long seq;
    private UserRes userMale;
    private UserRes userFemale;
    private LocalDateTime createdAt;

    @Builder
    public ChatRoomRes(ChatRoom chatRoom) {
        this.seq = chatRoom.getSeq();
        this.userMale = new UserRes(chatRoom.getUserMale());
        this.userFemale = new UserRes(chatRoom.getUserFemale());
        this.createdAt = chatRoom.getCreatedAt();
    }


    }
