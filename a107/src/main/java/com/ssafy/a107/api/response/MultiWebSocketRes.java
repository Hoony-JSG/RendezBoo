package com.ssafy.a107.api.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultiWebSocketRes {

    public enum MultiWebSocketFlag {
        JOIN, CHAT, EXIT

    }

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MultiJoinRes {
        private MultiWebSocketFlag flag;
        private Long maleNum;
        private Long femaleNum;
        private Long multiMeetingRoomSeq;
        private Long senderSeq;
        private LocalDateTime createdAt;

    }
}
