package com.ssafy.a107.api.response.game;

import com.ssafy.a107.api.response.MultiChatFlag;
import com.ssafy.a107.db.entity.game.BR31;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BR31Res {

    private MultiChatFlag flag;

    private Long multiMeetingRoomSeq;

    private String message;

    private Integer point;

    private List<Long> order;

    private Long nextUser;

    private GameType gameType;

    public BR31Res(BR31 br31, String message, List<Long> order, Long nextUser, MultiChatFlag flag) {
        this.multiMeetingRoomSeq = br31.getMultiMeetingRoomSeq();
        this.point = br31.getPoint();
        this.order = order;
        this.nextUser = nextUser;
        this.message = message;
        this.flag = flag;
        this.gameType = GameType.BR31;
    }
}
