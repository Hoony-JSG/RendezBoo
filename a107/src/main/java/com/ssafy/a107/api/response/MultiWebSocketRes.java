package com.ssafy.a107.api.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MultiWebSocketRes {

    private MultiChatFlag flag;
    private Long maleNum;
    private Long femaleNum;
    private String message;
    private Long multiMeetingRoomSeq;
    private Long senderSeq;
    private LocalDateTime createdAt;


}
