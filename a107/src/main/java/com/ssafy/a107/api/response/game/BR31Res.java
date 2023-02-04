package com.ssafy.a107.api.response.game;

import com.ssafy.a107.api.response.MultiChatFlag;
import com.ssafy.a107.db.entity.game.BR31;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BR31Res {

    private MultiChatFlag flag;

    private Long sessionId;

    private Long multiMeetingRoomSeq;

    private String message;

    private Integer point;

    private Long nextUser;

    public BR31Res(BR31 br31, String message, Long nextUser, MultiChatFlag flag) {
        this.sessionId = br31.getSessionId();
        this.multiMeetingRoomSeq = br31.getMultiMeetingRoomSeq();
        this.point = br31.getPoint();
        this.nextUser = nextUser;
        this.message = message;
        this.flag = flag;
    }

}
