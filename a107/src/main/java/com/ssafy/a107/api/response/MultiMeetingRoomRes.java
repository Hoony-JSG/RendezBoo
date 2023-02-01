package com.ssafy.a107.api.response;


import com.ssafy.a107.db.entity.MultiMeetingRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class MultiMeetingRoomRes {
    private final String title;
    private final int maleNum;
    private final int femaleNum;

    public MultiMeetingRoomRes(MultiMeetingRoom roomEntity, int maleNum, int femaleNum){
        this.title = roomEntity.getTitle();
        this.maleNum = maleNum;
        this.femaleNum = femaleNum;
    }
}
