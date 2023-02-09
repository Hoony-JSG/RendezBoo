package com.ssafy.a107.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OneToOneMeetingChatRes {

    public enum OneToOneChatFlag {
        JOIN, EXIT, SYSTEM, CHAT, PHASE1, PHASE2, PHASE3, FINAL
    }

    private OneToOneChatFlag flag;

    private Long senderSeq;

    private Long oneToOneMeetingRoomSeq;

    private String message;

    private LocalDateTime createdAt;


}
