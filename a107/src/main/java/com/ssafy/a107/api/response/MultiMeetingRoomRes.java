package com.ssafy.a107.api.response;


import com.ssafy.a107.db.entity.MultiMeetingRoom;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MultiMeetingRoomRes {
    private final String title;
    private final Long maleNum;
    private final Long femaleNum;

    @Builder
    public MultiMeetingRoomRes(MultiMeetingRoom roomEntity, Long maleNum, Long femaleNum){
        this.title = roomEntity.getTitle();
        this.maleNum = maleNum;
        this.femaleNum = femaleNum;
    }
}
