package com.ssafy.a107.api.response;

import com.ssafy.a107.db.entity.Stick;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StickRes {

    private Long multiMeetingRoomSeq;
    private Map<Long, Long> targets;
    private List<long[]> matches;
    private String message;
    private MultiChatFlag flag;

    public StickRes(Stick stick, List<long[]> matches, String message, MultiChatFlag flag) {
        this.multiMeetingRoomSeq = stick.getMultiMeetingRoomSeq();
        this.targets = stick.getTargets();
        this.matches = matches;
        this.message = message;
        this.flag = flag;
    }
}
